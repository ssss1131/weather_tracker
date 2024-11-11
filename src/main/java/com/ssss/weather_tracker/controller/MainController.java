package com.ssss.weather_tracker.controller;

import com.ssss.weather_tracker.service.OpenWeatherService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ssss.weather_tracker.util.Constants.HOME;
import static com.ssss.weather_tracker.util.Constants.HOME_VIEW;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final OpenWeatherService openWeatherService;

    @GetMapping(HOME)
    public String homeForm(HttpServletRequest request){
        System.out.println(openWeatherService.retrieveWeatherByCityName("Almaty"));
        return HOME_VIEW;
    }

}
