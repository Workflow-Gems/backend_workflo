package com.workflo.workflo_backend.user.services.impl;

import com.workflo.workflo_backend.appConfig.dtos.request.MailRequest;
import com.workflo.workflo_backend.appConfig.dtos.request.To;
import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.appConfig.services.MailService;
import com.workflo.workflo_backend.exceptions.*;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.models.*;
import com.workflo.workflo_backend.user.repository.UserRepository;
import com.workflo.workflo_backend.user.services.TokenService;
import com.workflo.workflo_backend.user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.workflo.workflo_backend.user.models.UserStatus.ACTIVE;
import static com.workflo.workflo_backend.user.models.UserStatus.PENDING;


@Service
@AllArgsConstructor
public class WorkFloUserService implements UserService {

    private final UserRepository userRepository;
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
    public User createUserAccount(UserRequest request) throws SendMailException {
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
        String locator = "http://localhost:8080/api/v1/user/confirm?email=%s&token=%s";
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
        User user = getUser(request.getUserId());
        Address address = new Address();
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setZipCode(Long.parseLong(request.getZipCode()));
        user.getAccount().setAddress(address);
        userRepository.save(user);
        return "Address saved successfully...";
    }
    @Override
    public String createProfile(ProfileRequest request) throws UserNotFoundException, CloudUploadException {
        User user = getUser(request.getUserId());
        String image = cloudService.upload(request.getImage());
        Profile profile = modelMapper.map(request, Profile.class);
        profile.setImage(image);
        user.getAccount().setProfile(profile);
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

    private User getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found..."));
    }
}
