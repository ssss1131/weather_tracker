package com.ssss.weather_tracker.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class CookieHelper {

    public static void createCookie(String name, String value, int maxAge, HttpServletResponse response){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static Optional<String> extractSessionIdFromCookie(HttpServletRequest request, String sessionName) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(sessionName))
                .map(Cookie::getValue)
                .findFirst();
    }

    public static void invalidateCookie(String cookieName, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
