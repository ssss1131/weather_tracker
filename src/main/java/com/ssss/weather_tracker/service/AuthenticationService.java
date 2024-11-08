package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.exception.UserAlreadyExistsException;
import com.ssss.weather_tracker.model.User;
import com.ssss.weather_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public User register(UserRegistrationDto userDto){
        User user = User.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        if(userRepository.existsByLogin(user.getLogin())){
           throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists.");
        }
        return userRepository.save(user);
    }


    public Optional<User> login(UserLoginDto userDto) {
        Optional<User>  foundUser = userRepository.find(userDto.getLogin());
        //TODO Add BCrypt for userDto.password()
        return foundUser.filter(user -> user.getPassword().equals(userDto.getPassword()));

    }
}
