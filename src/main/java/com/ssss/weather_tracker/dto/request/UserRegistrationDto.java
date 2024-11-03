package com.ssss.weather_tracker.dto.request;

import com.ssss.weather_tracker.validation.PasswordMatches;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class UserRegistrationDto {

    @NotNull
    @Size(min = 2, max = 30, message = "Login must be between 2 and 30 characters")
    private String login;

    @NotNull
    @Size(min = 3, max = 60, message = "Password must be between 3 and 60 characters")
    private String password;

    @NotNull(message = "Please confirm your password")
    private String confirmPassword;

}
