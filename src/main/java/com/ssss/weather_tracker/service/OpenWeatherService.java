package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.dto.response.api.GeoLocationDto;
import com.ssss.weather_tracker.dto.response.api.WeatherDto;
import com.ssss.weather_tracker.exception.api.BadRequestException;
import com.ssss.weather_tracker.exception.api.OpenWeatherServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.ssss.weather_tracker.util.Constants.DIRECT_PATH;
import static com.ssss.weather_tracker.util.Constants.WEATHER_PATH;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {

    private final RestClient restClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.locations.limit}")
    private int limit;

    public List<WeatherDto> retrieveWeatherByCityName(String name) {
        String uri = String.format(DIRECT_PATH + "?q=%s&limit=%s&appid=%s", name, limit, apiKey);
        List<GeoLocationDto> body = restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode == HttpStatus.UNAUTHORIZED, (request, response) -> {
                    throw new BadRequestException("Please write proper API key");
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new OpenWeatherServerException("Server error occurred");
                }))
                .body(new ParameterizedTypeReference<>() {
                });

        List<WeatherDto> weatherDataList  = new ArrayList<>();
        for (GeoLocationDto geoLocationDto : body) {
            WeatherDto weatherDto = retrieveWeatherByCoordinates(geoLocationDto.getLatitude(), geoLocationDto.getLongitude());
            setValidInfo(geoLocationDto, weatherDto);
            weatherDataList.add(weatherDto);
        }
        return weatherDataList;
    }

    public WeatherDto retrieveWeatherByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        String uri = String.format(WEATHER_PATH +"?lat=%s&lon=%s&appid=%s&units=metric", latitude, longitude, apiKey);
        return restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode == HttpStatus.UNAUTHORIZED, (request, response) -> {
                    throw new BadRequestException("Please write proper API key");
                })
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) ->{
                    throw new BadRequestException("No location details for the given coordinates");
                })
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new OpenWeatherServerException("Server error occurred");
                }))
                .body(new ParameterizedTypeReference<>() {});
    }

    private void setValidInfo(GeoLocationDto geoLocationDto, WeatherDto weatherDto) {
        weatherDto.setName(geoLocationDto.getName());
        weatherDto.setState(geoLocationDto.getState());
    }
}
