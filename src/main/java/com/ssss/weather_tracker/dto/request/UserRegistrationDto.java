package com.ssss.weather_tracker.dto.request;

import com.ssss.weather_tracker.validation.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "Login cannot be empty or whitespace")
    @Size(min = 2, max = 30, message = "Login must be between 2 and 30 characters")
    private String login;

    @NotBlank(message = "password cannot be empty or whitespace")
    @Size(min = 3, max = 60, message = "Password must be between 3 and 60 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

}
