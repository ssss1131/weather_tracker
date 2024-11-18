package com.ssss.weather_tracker.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LocationSearchResultDto {

    private String name;

    private String state;

    private String country;

    private BigDecimal temp;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private boolean liked;


}
