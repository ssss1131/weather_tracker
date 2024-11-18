package com.ssss.weather_tracker.controller;


import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.service.AuthenticationService;
import com.ssss.weather_tracker.util.CookieHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.ssss.weather_tracker.util.Constants.*;

@Controller
@RequestMapping(AUTH_BASE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @Value("${session.cookie.max-age}")
    private int sessionCookieMaxAge;

    @Value("${session.cookie.name}")
    private String sessionCookieName;

    @GetMapping(LOGIN)
    public String loginForm(@ModelAttribute(USER_ATTRIBUTE) UserLoginDto user) {
        return LOGIN_VIEW;
    }

    @PostMapping(LOGIN)
    public String login(@ModelAttribute(USER_ATTRIBUTE) @Valid UserLoginDto userDto,
                        BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return LOGIN_VIEW;
        }
        UUID uuid = authenticationService.authenticate(userDto);
        CookieHelper.createCookie(sessionCookieName, String.valueOf(uuid), sessionCookieMaxAge, response);
        return REDIRECT_HOME;
    }


    @GetMapping(REGISTER)
    public String registerForm(@ModelAttribute(USER_ATTRIBUTE) UserRegistrationDto user) {
        return REGISTER_VIEW;
    }


    @PostMapping(REGISTER)
    public String register(@ModelAttribute(USER_ATTRIBUTE) @Valid UserRegistrationDto user,
                           BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return REGISTER_VIEW;
        }
        UUID uuid = authenticationService.register(user);
        CookieHelper.createCookie(sessionCookieName, uuid.toString(), sessionCookieMaxAge, response);
        return REDIRECT_HOME;
    }

    @PostMapping(SIGNOUT)
    public String signOut(@CookieValue(value = "${session.cookie.name}", defaultValue = "") String sessionId, HttpServletResponse response) {
        authenticationService.signOut(sessionId, sessionCookieName, response);
        return REDIRECT_LOGIN;
    }


}
