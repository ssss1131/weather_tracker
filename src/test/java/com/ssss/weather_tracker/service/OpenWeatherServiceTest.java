package com.ssss.weather_tracker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssss.weather_tracker.dto.response.api.GeoLocationDto;
import com.ssss.weather_tracker.dto.response.api.WeatherDto;
import com.ssss.weather_tracker.exception.api.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.client.RestClient.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"weather.api.key=test_api_key", "weather.api.locations.limit=1"})
class OpenWeatherServiceTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private OpenWeatherService openWeatherService;

    private RequestHeadersUriSpec requestHeadersUriSpec;
    private ResponseSpec responseSpec;

    private String mockResponseWeather;

    @BeforeEach
    void setup() {
        requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        responseSpec = mock(ResponseSpec.class);

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

        ReflectionTestUtils.setField(openWeatherService, "apiKey", "test_api_key");
        ReflectionTestUtils.setField(openWeatherService, "limit", 1);
        mockResponseWeather = """
        {
            "name": "New York",
            "coord": {
                "lon": -74.006,
                "lat": 40.7128
            },
            "weather": [
                {
                    "description": "clear sky",
                    "icon": "01d"
                }
            ],
            "main": {
                "temp": 22.5,
                "pressure": 1013,
                "humidity": 53
            },
            "sys": {
                "country": "US"
            }
        }
        """;


    }


    @Test
    void retrieveWeatherByCityName_ValidRequest_ValidMapping() throws IOException {
        String name = "New York";
        BigDecimal lat = BigDecimal.valueOf(40.7128);
        BigDecimal lon = BigDecimal.valueOf(-74.006);
        String country = "US";


        String mockResponseGeo = """
                [
                    {
                        "name": "%s",
                        "lat": %s,
                        "lon": %s,
                        "country": "%s"
                    }
                ]
                """.formatted(name, lat, lon, country);


        ObjectMapper objectMapper = new ObjectMapper();
        List<GeoLocationDto> geoLocationResponse = objectMapper.readValue(mockResponseGeo, new TypeReference<>() {
        });
        WeatherDto weatherResponse = objectMapper.readValue(mockResponseWeather, WeatherDto.class);
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(geoLocationResponse)
                .thenReturn(weatherResponse);

        List<WeatherDto> byName = openWeatherService.retrieveWeatherByCityName(name);

        assertEquals(1, byName.size());
        WeatherDto city = byName.get(0);
        assertEquals(name, city.getName());
        assertEquals(lat, city.getCoordinates().getLatitude());
        assertEquals(lon, city.getCoordinates().getLongitude());
        assertEquals(country, city.getInfo().getCountry());
    }


    @Test
    void retrieveWeatherByCityName_ServerError_ThrowOpenWeatherServerException() {
        String cityName = "InvalidCity";

        when(responseSpec.body(any(ParameterizedTypeReference.class))).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> openWeatherService.retrieveWeatherByCityName(cityName));
    }


}