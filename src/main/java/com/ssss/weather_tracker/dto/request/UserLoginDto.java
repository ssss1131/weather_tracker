package com.ssss.weather_tracker.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    @NotBlank(message = "Login cannot be empty or whitespace")
    @Size(min = 2, max = 30, message = "Login must be between 2 and 30 characters")
    private String login;

    @NotBlank(message = "Password cannot be empty or whitespace")
    @Size(min = 3, max = 60, message = "Password must be between 3 and 60 characters")
    private String password;

}
