package com.ssss.weather_tracker.controller;


import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import com.ssss.weather_tracker.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginForm(){
        return "/auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") @Valid UserLoginDto user,
                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/auth/login";
        }
        System.out.println(user);
        return "redirect:/main/home";
    }

    

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("user") UserRegistrationDto user){
        return "/auth/register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid UserRegistrationDto user,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/auth/register";
        }

        return "redirect:/main/home";
    }


}
