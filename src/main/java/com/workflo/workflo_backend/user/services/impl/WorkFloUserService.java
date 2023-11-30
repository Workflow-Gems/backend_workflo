package com.workflo.workflo_backend.user.services.impl;

import com.workflo.workflo_backend.appConfig.services.CloudService;
import com.workflo.workflo_backend.exceptions.CloudUploadException;
import com.workflo.workflo_backend.exceptions.UserNotFoundException;
import com.workflo.workflo_backend.user.dtos.request.AddressRequest;
import com.workflo.workflo_backend.user.dtos.request.ProfileRequest;
import com.workflo.workflo_backend.user.dtos.request.UserRequest;
import com.workflo.workflo_backend.user.dtos.response.UserResponse;
import com.workflo.workflo_backend.user.models.Account;
import com.workflo.workflo_backend.user.models.Address;
import com.workflo.workflo_backend.user.models.Profile;
import com.workflo.workflo_backend.user.models.User;
import com.workflo.workflo_backend.user.repository.UserRepository;
import com.workflo.workflo_backend.user.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class WorkFloUserService implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

    @Override
    public UserResponse createUser(UserRequest request) {
        Account account = modelMapper.map(request, Account.class);
        User user = modelMapper.map(request, User.class);
        user.setAccount(account);
        User savedUser = userRepository.save(user);
        return generatedUserResponse(savedUser);
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
        Address address = modelMapper.map(request, Address.class);
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
    private User getUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found..."));
        return user;
    }
}
