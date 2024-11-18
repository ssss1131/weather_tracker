package com.ssss.weather_tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedLocationDto {

    private long id;

    private String name;

    private String state;

    private String country;

    private BigDecimal temp;

    private BigDecimal pressure;

    private BigDecimal humidity;

    private String weatherIcon;

    private String weatherDescription;

}
