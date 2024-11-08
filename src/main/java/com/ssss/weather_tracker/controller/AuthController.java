package com.ssss.weather_tracker.controller;


import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.model.User;
import com.ssss.weather_tracker.service.AuthenticationService;
import com.ssss.weather_tracker.service.SessionService;
import com.ssss.weather_tracker.util.CookieHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final SessionService sessionService;

    @Value("${session.cookie.max-age}")
    private int sessionCookieMaxAge;

    @Value("${session.cookie.name}")
    private String sessionCookieName;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("user") UserLoginDto user) {
        return "/auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserLoginDto userDto,
                        BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "/auth/login";
        }
        Optional<User> maybeFoundUser = authenticationService.login(userDto);
        if(maybeFoundUser.isEmpty()){
            bindingResult.addError(new FieldError("user", "login", "Login or Password is incorrect"));
            return "/auth/login";
        }
        User user = maybeFoundUser.get();
        sessionService.refreshSession(user, sessionCookieMaxAge);
        return "redirect:/home";
    }

    

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("user") UserRegistrationDto user){
        return "/auth/register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserRegistrationDto user,
                           BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "/auth/register";
        }
        User registeredUser = authenticationService.register(user);
        UUID uuid = sessionService.createSession(registeredUser, sessionCookieMaxAge);
        CookieHelper.createCookie(sessionCookieName, uuid.toString(), sessionCookieMaxAge, response);
        return "redirect:/home";
    }


}
