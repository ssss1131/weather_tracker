package com.ssss.weather_tracker.dto.response.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private String name;

    private String state;

    @JsonProperty("coord")
    private Coordinates coordinates;

    @JsonProperty("sys")
    private LocationInfo info;

    @JsonProperty("weather")
    private List<WeatherType> weatherType;

    @JsonProperty("main")
    private WeatherInfo weatherInfo;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherType {

        private String description;

        private String icon;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocationInfo {

        private String country;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo {

        private BigDecimal temp;

        private int pressure;

        private int humidity;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coordinates {

        @JsonProperty("lat")
        private BigDecimal latitude;

        @JsonProperty("lon")
        private BigDecimal longitude;

    }
}
