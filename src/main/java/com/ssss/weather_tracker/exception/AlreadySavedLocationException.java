package com.ssss.weather_tracker.exception;

public class AlreadySavedLocationException extends RuntimeException {

    public AlreadySavedLocationException(String message) {
        super(message);
    }
}
