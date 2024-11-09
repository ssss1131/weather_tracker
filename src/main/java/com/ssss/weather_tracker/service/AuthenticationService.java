package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.exception.AuthenticationException;
import com.ssss.weather_tracker.exception.UserAlreadyExistsException;
import com.ssss.weather_tracker.model.Session;
import com.ssss.weather_tracker.model.User;
import com.ssss.weather_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Value("${session.cookie.max-age}")
    private int sessionCookieMaxAge;

    public UUID register(UserRegistrationDto userDto){
        User user = User.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        if(userRepository.existsByLogin(user.getLogin())){
           throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists.");
        }
        User registeredUser = userRepository.save(user);
        return sessionService.createSession(registeredUser, sessionCookieMaxAge);
    }


    public UUID authenticate(UserLoginDto userDto) throws AuthenticationException {
        User user = userRepository.find(userDto.getLogin())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

        // TODO: Add BCrypt comparison for userDto.password()
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new AuthenticationException("Invalid username or password");
        }

        return sessionService.refreshSession(user, sessionCookieMaxAge);
    }

}
