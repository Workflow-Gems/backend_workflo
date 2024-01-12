package com.workflo.workflo_backend.user.services.impl;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.project.dtos.response.ProjectResponse;
import com.workflo.workflo_backend.project.entities.Project;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UpdateUserRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.FoundUserResponse;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.models.Account;
import com.workflo.workflo_backend.user.models.Address;
import com.workflo.workflo_backend.user.models.Profile;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.repository.UserRepository;
import com.workflo.workflo_backend.user.services.ProfileService;
import com.workflo.workflo_backend.user.services.TokenService;
import com.workflo.workflo_backend.user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.workflo.workflo_backend.user.models.UserStatus.ACTIVE;
import static com.workflo.workflo_backend.user.models.UserStatus.PENDING;


@Service
@Slf4j
@AllArgsConstructor
public class WorkFloUserService implements UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;
    private final TokenService tokenService;
    private final MailService mailService;
    private final Context context;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) throws SendMailException {
        Optional<User> foundUser = getUserByEmail(request.getEmail());
        if (foundUser.isPresent()) throw new DuplicatedUserEmailException(
                String.format("User with this email %s already exist", request.getEmail()));
        User savedUser = createUserAccount(request);
        return generatedUserResponse(savedUser);
    }
    @Transactional
    private User createUserAccount(UserRequest request) throws SendMailException {
        Account account = modelMapper.map(request, Account.class);
        User user = modelMapper.map(request, User.class);
        user.setAccount(account);
        user.setUserStatus(PENDING);
        User savedUser = userRepository.save(user);
        createMailRequest(savedUser);
        return savedUser;
    }
    private void createMailRequest(User user) throws SendMailException {
        String token = tokenService.generateToken(user);
        String locator = "https://backendworkflo-production.up.railway.app/api/v1/user/confirm?email=%s&token=%s";
        String url = String.format(locator,user.getEmail(), token);
        setContext(user, url);
    }
    private void setContext(User user, String url) throws SendMailException {
        context.setVariables(
                Map.of("firstName",user.getFirstName(),
                        "url", url
                )
        );
        String template = "verification_mail";
       createMailerRequest(user, template);
    }
    private void setContext(User user) throws SendMailException {
        context.setVariable("firstName", user.getFirstName());
        String template = "token_confirmed";
        createMailerRequest(user, template);
    }
    private void createMailerRequest(User user, String template) throws SendMailException {
        MailRequest request = new MailRequest();
        request.setSubject("Welcome to WorkFlo...");
        request.setHtmlContent("");
        To to = new To(user.getFirstName(), user.getEmail());
        request.setTo(List.of(to));
        sendMailer(request, template);
    }
    private void sendMailer(MailRequest request, String template) throws SendMailException {
        mailService.sendMail(request, template, context);
    }
    private Optional<User> getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    private static UserResponse generatedUserResponse(User savedUser) {
        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                "Account created successfully..."
        );
    }
    @Override
    public String createAddress(AddressRequest request) throws UserNotFoundException {
        User user = getUserWithId(request.getUserId());
        Address address = modelMapper.map(request, Address.class);
        address.setZipCode(Long.parseLong(request.getZipCode()));
        user.getAccount().setAddress(address);
        userRepository.save(user);
        return "Address saved successfully...";
    }
    @Override
    @Transactional
    public String createProfile(ProfileRequest request) throws UserNotFoundException, CloudUploadException, UserNotVerifiedException {
        User user = getUserWithId(request.getUserId());
        if (user.getUserStatus() == ACTIVE) {
            return generateUserProfile(request, user);
        }
        throw new UserNotVerifiedException(String
                .format("dear %s,kindly check your mail to confirm email before you can continue", user.getFirstName()));
    }
//    @Transactional
    private String generateUserProfile(ProfileRequest request, User user) throws CloudUploadException {
        Profile profile = modelMapper.map(request, Profile.class);
        if (request.getImage() != null) {
            profile.setImage(cloudService.upload(request.getImage()));
        }
        user.getAccount().setProfile(profile);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        return "Profile update successfully...";
    }
    @Override
    @Transactional
    public String confirmToken(String email, String token) throws TokenExceptions, SendMailException {
        String response = tokenService.confirmToken(email, token);
        if (response != null){
            Optional<User> foundUser = getUserByEmail(email);
            User user = foundUser.get();
            user.setUserStatus(ACTIVE);
            User savedUser = userRepository.save(user);
            setContext(savedUser);
            return response;
        }
        throw new TokenExceptions("not valid...");
    }
    @Override
    public FoundUserResponse findProjectedUserById(Long id) throws UserNotFoundException {
       User user = getUserWithId(id);
        return modelMapper.map(user, FoundUserResponse.class);
    }
    public User getUserWithId(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found..."));
    }
    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) throws UserNotFoundException, UpdateNotAllowedException {
        User user = getUserWithId(id);
        if (user.getAccount().getProfile() != null && user.getAccount().getAddress() != null) {
            ExtractedUpdate update = getExtractedUpdate(user);
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(request, update.address());
            modelMapper.map(request, update.profile());
            modelMapper.map(request, update.account());
            modelMapper.map(request, user);
            User savedUser = userRepository.save(user);
            UserResponse response = modelMapper.map(savedUser, UserResponse.class);
            response.setMessage("user profile updated successfully...");
            return response;
        }
        throw new UpdateNotAllowedException("kindly complete your profile and contact information to complete this action");
    }
    @Override
    public String uploadProfilePicture(long id, MultipartFile multipart) throws UserNotFoundException, CloudUploadException {
        User user = getUserWithId(id);
        String url = cloudService.upload(multipart);
        user.getAccount().getProfile().setImage(url);
        userRepository.save(user);
        return url;
    }

    @Override
    @Transactional
    public List<ProjectResponse> viewJoinedProjectsByUser(long id) throws UserNotFoundException, ProjectNotExistException {
        User user = getUserWithId(id);
        if (!user.getJoinedProjects().isEmpty()){
            List<ProjectResponse> responses = new ArrayList<>();
            for (Project project : user.getJoinedProjects()){
                responses.add(modelMapper.map(project, ProjectResponse.class));
            }
            return responses;
        }
        throw new ProjectNotExistException("you have no joined projects...");
    }


    private static ExtractedUpdate getExtractedUpdate(User user) {
        Address address = user.getAccount().getAddress();
        Profile profile = user.getAccount().getProfile();
        Account account = user.getAccount();
        return new ExtractedUpdate(address, profile, account);
    }
    private record ExtractedUpdate(Address address, Profile profile, Account account) {
    }
    public FoundUserResponse getUserById(Long id) throws UserNotFoundException {
        User user = getUserWithId(id);
        return modelMapper.map(user, FoundUserResponse.class);
    }

    @Override
    public List<Profile> searchByJobTitleOrSkills(String jobTitleOrSkill) {
        return profileService.searchProfile(jobTitleOrSkill);
    }

}
