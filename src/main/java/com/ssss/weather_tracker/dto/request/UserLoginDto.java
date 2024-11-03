package com.ssss.weather_tracker.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    @NotNull(message = "Login cannot be empty")
    private String login;

    @NotNull(message = "Password cannot be null")
    private String password;

}
