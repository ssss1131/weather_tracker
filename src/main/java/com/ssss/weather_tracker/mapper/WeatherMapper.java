package com.ssss.weather_tracker.mapper;

import com.ssss.weather_tracker.dto.response.LikedLocationDto;
import com.ssss.weather_tracker.dto.response.LocationSearchResultDto;
import com.ssss.weather_tracker.dto.response.api.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WeatherMapper {

    WeatherMapper INSTANCE = Mappers.getMapper(WeatherMapper.class);

    @Mapping(source = "info.country", target = "country")
    @Mapping(source = "weatherInfo.temp", target = "temp")
    @Mapping(source = "coordinates.latitude", target = "latitude")
    @Mapping(source = "coordinates.longitude", target = "longitude")
    LocationSearchResultDto toLocationSearchResultDto(WeatherDto weatherDto);


    @Mapping(source = "info.country", target = "country")
    @Mapping(source = "weatherInfo.temp", target = "temp")
    @Mapping(source = "weatherInfo.pressure", target = "pressure")
    @Mapping(source = "weatherInfo.humidity", target = "humidity")
    @Mapping(source = "weatherType", target = "weatherIcon", qualifiedByName = "mapWeatherIcon")
    LikedLocationDto toLikedLocationDto(WeatherDto weatherDto);

    @Named("mapWeatherIcon")
    default String mapWeatherIcon(List<WeatherDto.WeatherType> weatherType) {
        return (weatherType != null && !weatherType.isEmpty()) ? weatherType.get(0).getIcon() : null;
    }



}
