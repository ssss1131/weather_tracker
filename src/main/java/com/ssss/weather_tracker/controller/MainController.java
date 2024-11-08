package com.ssss.weather_tracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/home")
    public String homeForm(HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");
        System.out.println(userId);
        return "/main/home";
    }

}
