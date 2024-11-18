package com.ssss.weather_tracker.exception;

import com.ssss.weather_tracker.dto.request.UserLoginDto;
import com.ssss.weather_tracker.dto.request.UserRegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.ssss.weather_tracker.util.Constants.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistsException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage());
        mav.addObject(USER_ATTRIBUTE, new UserRegistrationDto());
        mav.setViewName(REGISTER_VIEW);
        return mav;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleAuthenticationException(AuthenticationException ex){
        ModelAndView mav = new ModelAndView();
        mav.addObject(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage());
        mav.addObject(USER_ATTRIBUTE, new UserLoginDto());
        mav.setViewName(LOGIN_VIEW);
        return mav;
    }

    @ExceptionHandler(AlreadySavedLocationException.class)
    public String handleAlreadySavedLocationException(AlreadySavedLocationException ex,
                                                            HttpServletRequest request,
                                                            RedirectAttributes redirectAttributes
                                                            ){
        String query = request.getParameter(QUERY_ATTRIBUTE);
        redirectAttributes.addFlashAttribute(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage());
        return "redirect:/search?query=" + query;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(ERROR_404_VIEW);
        mav.addObject(ERROR_MESSAGE_ATTRIBUTE, "The page you are looking for was not found.");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handle500(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(ERROR_500_VIEW);
        mav.addObject(ERROR_MESSAGE_ATTRIBUTE, "An unexpected error occurred.");
        return mav;
    }

}
