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
    public static final String SEARCH = "/search";
    public static final String SIGN_OUT ="/logout";
    public static final String DELETE_LOCATION = "locations/delete/{id}";
    public static final String ADD_LOCATION = "locations/add";

    public static final String LOGIN_VIEW = "/auth/login";
    public static final String REGISTER_VIEW = "/auth/register";
    public static final String HOME_VIEW = "/main/home";
    public static final String SEARCH_RESULT_VIEW = "/main/search";
    public static final String ERROR_404_VIEW = "/error/404";
    public static final String ERROR_500_VIEW = "/error/500";

    public static final String USER_ATTRIBUTE = "user";
    public static final String USER_ID_ATTRIBUTE = "userId";
    public static final String USER_NAME_ATTRIBUTE = "userName";
    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    public static final String LOCATION_ATTRIBUTE = "locations";
    public static final String QUERY_ATTRIBUTE = "query";

    private static final String REDIRECT = "redirect:";
    public static final String REDIRECT_HOME = REDIRECT + HOME;
    public static final String REDIRECT_LOGIN = REDIRECT + AUTH_LOGIN;
    public static final String REDIRECT_SEARCH = REDIRECT + SEARCH + "?query=";

    public static final String DIRECT_PATH = "/geo/1.0/direct";
    public static final String WEATHER_PATH = "/data/2.5/weather";
}
