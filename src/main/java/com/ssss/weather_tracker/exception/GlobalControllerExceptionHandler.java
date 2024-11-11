package com.ssss.weather_tracker.exception;

import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static com.ssss.weather_tracker.util.Constants.LOGIN_VIEW;
import static com.ssss.weather_tracker.util.Constants.REGISTER_VIEW;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistsException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("user", new UserRegistrationDto());
        mav.setViewName(REGISTER_VIEW);
        return mav;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(AuthenticationException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("user", new UserLoginDto());
        mav.setViewName(LOGIN_VIEW);
        return mav;
    }
//
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
//            throw e;
//        }
////        logger.error("Request URL: " + req.getRequestURL() + " raised " + e);
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", e);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("/error/error");
//        return mav;
//    }
}
