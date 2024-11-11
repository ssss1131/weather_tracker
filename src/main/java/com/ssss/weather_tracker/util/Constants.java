package com.ssss.weather_tracker.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String AUTH_BASE = "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String AUTH_LOGIN = AUTH_BASE + LOGIN;
    public static final String AUTH_REGISTER = AUTH_BASE + REGISTER;
    public static final String HOME = "/home";

    public static final String LOGIN_VIEW = "/auth/login";
    public static final String REGISTER_VIEW = "/auth/register";
    public static final String HOME_VIEW = "/main/home";

    public static final String USER_ATTRIBUTE = "user";
    public static final String REDIRECT_HOME = "redirect:/home";

    public static final String DIRECT_PATH = "/geo/1.0/direct";
    public static final String WEATHER_PATH = "/data/2.5/weather";
}
