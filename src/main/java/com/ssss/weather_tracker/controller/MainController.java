package com.ssss.weather_tracker.controller;

import com.ssss.weather_tracker.dto.response.LikedLocationDto;
import com.ssss.weather_tracker.dto.response.LocationSearchResultDto;
import com.ssss.weather_tracker.model.Location;
import com.ssss.weather_tracker.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ssss.weather_tracker.util.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final UserActionService userActionService;

    @GetMapping(HOME)
    public String homeForm(@RequestAttribute(USER_ID_ATTRIBUTE) long id, Model model) {
        List<LikedLocationDto> likedLocations = userActionService.getLikedLocations(id);
        model.addAttribute(LOCATION_ATTRIBUTE, likedLocations);
        return HOME_VIEW;
    }


    @GetMapping(SEARCH)
    public String searchForm(@ModelAttribute(QUERY_ATTRIBUTE) String query,
                             @RequestAttribute(USER_ID_ATTRIBUTE) long id,
                             Model model) {
        if(query.trim().isEmpty()){
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE,"Query must not be empty.");
            model.addAttribute(LOCATION_ATTRIBUTE, List.of());
            return SEARCH_RESULT_VIEW;
        }

        List<LocationSearchResultDto> searchResults = userActionService.getSearchResults(query, id);
        model.addAttribute(LOCATION_ATTRIBUTE, searchResults);
        return SEARCH_RESULT_VIEW;
    }

    @DeleteMapping(DELETE_LOCATION)
    public String deleteLocation(@PathVariable("id") Long id, @RequestAttribute(USER_ID_ATTRIBUTE) long userId) {
        userActionService.deleteLocationById(id, userId);
        return REDIRECT_HOME;
    }

    @PostMapping(ADD_LOCATION)
    public String addLocation(@RequestAttribute(USER_ID_ATTRIBUTE) long userId, @RequestParam(QUERY_ATTRIBUTE) String query,
                              @ModelAttribute Location location) {
        userActionService.addLocation(location, userId);
        return REDIRECT_SEARCH + query;
    }

}
