package com.ssss.weather_tracker.controller.interceptor;

import com.ssss.weather_tracker.model.Session;
import com.ssss.weather_tracker.service.SessionService;
import com.ssss.weather_tracker.util.CookieHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

import static com.ssss.weather_tracker.util.Constants.*;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Value("${session.cookie.name}")
    private String sessionCookieName;

    @Value("${session.cookie.max-age}")
    private int sessionCookieMaxAge;

    /**
     * Pre handle method for checking and setting if exists user with such session.
     * <p>
     * This method extract session from request and
     * if session is not exists redirect to login page(if not already there).
     * if session exists in cookies:
     *   1.Check for existing in database ->
     *   if not exist redirect to login
     *   if exists we check its expiration time and if it is close, we update it
     *   2.Then we set user to request attributes for rendering page to exact user.
     * </p>
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        Optional<String> sessionIdOpt = CookieHelper.extractSessionIdFromCookie(request, sessionCookieName);

        if (sessionIdOpt.isPresent() && processExistingSession(sessionIdOpt.get(), request, response, requestURI)) {
            return true;
        }

        if (isAuthPage(requestURI)) {
            return true;
        }

        response.sendRedirect(AUTH_LOGIN);
        return false;
    }

    private boolean processExistingSession(String sessionId, HttpServletRequest request, HttpServletResponse response, String requestURI) throws Exception {
        Optional<Session> sessionOpt = sessionService.getSessionWithUser(sessionId);

        if (sessionOpt.isEmpty()) {
            return false;
        }

        Session session = sessionOpt.get();
        if (isAuthPage(requestURI)) {
            response.sendRedirect(HOME);
            return false;
        }

        if (sessionService.isNearExpiration(session)) {
            sessionService.refreshSession(session.getId(), sessionCookieMaxAge);
            CookieHelper.createCookie(sessionCookieName, sessionId, sessionCookieMaxAge, response);
        }

        request.setAttribute("userId", session.getUser().getId());
        return true;
    }

    private static boolean isAuthPage(String requestURI) {
        return requestURI.equals(AUTH_LOGIN) || requestURI.equals(AUTH_REGISTER);
    }




}
