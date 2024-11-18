package com.ssss.weather_tracker.service;

import com.ssss.weather_tracker.dto.response.LikedLocationDto;
import com.ssss.weather_tracker.dto.response.LocationSearchResultDto;
import com.ssss.weather_tracker.dto.response.api.WeatherDto;
import com.ssss.weather_tracker.mapper.WeatherMapper;
import com.ssss.weather_tracker.model.Location;
import com.ssss.weather_tracker.model.User;
import com.ssss.weather_tracker.repository.LocationRepository;
import com.ssss.weather_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActionService {

    private final OpenWeatherService openWeatherService;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final WeatherMapper mapper = WeatherMapper.INSTANCE;

    public List<LocationSearchResultDto> getSearchResults(String query, long id) {
        List<WeatherDto> weatherDtoList = openWeatherService.retrieveWeatherByCityName(query);
        List<Location> likedLocations = locationRepository.getAllByUserId(id);
        return mapToLocationSearchResultDtoList(weatherDtoList, likedLocations);
    }



    public void deleteLocationById(Long locationId, Long userId) {
        locationRepository.deleteById(locationId, userId);
    }

    public List<LikedLocationDto> getLikedLocations(long id) {
        List<Location> locations = locationRepository.getAllByUserId(id);
        return locations.stream().map(
                location -> {
                    LikedLocationDto likedLocationDto = mapper.toLikedLocationDto(openWeatherService.retrieveWeatherByCoordinates(location.getLatitude(), location.getLongitude()));
                    likedLocationDto.setId(location.getId());
                    likedLocationDto.setName(location.getName());
                    likedLocationDto.setState(location.getState());
                    return likedLocationDto;
                }
        ).collect(Collectors.toList());
    }

    public void addLocation(Location location, long id) {
        User user = userRepository.findById(id);
        location.setUser(user);
        locationRepository.save(location);
    }

    private List<LocationSearchResultDto> mapToLocationSearchResultDtoList(List<WeatherDto> weatherDtos, List<Location> likedLocations) {
        return weatherDtos.stream()
                .map(dto -> {
                    LocationSearchResultDto resultDto = mapper.toLocationSearchResultDto(dto);
                    resultDto.setLiked(isLocationLiked(dto, likedLocations));
                    return resultDto;
                })
                .collect(Collectors.toList());
    }

    private boolean isLocationLiked(WeatherDto weatherDto, List<Location> likedLocations) {
        return likedLocations.stream().anyMatch(location ->
                location.getLatitude().compareTo(weatherDto.getCoordinates().getLatitude()) == 0 &&
                location.getLongitude().compareTo(weatherDto.getCoordinates().getLongitude()) == 0
        );
    }
}
