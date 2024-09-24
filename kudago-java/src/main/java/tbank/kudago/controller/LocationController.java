package tbank.kudago.controller;

import felv.logger.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tbank.kudago.model.Location;
import tbank.kudago.repository.LocationRepository;

import java.util.List;

@RestController
@LogExecutionTime
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    @GetMapping()
    private List<Location> getAllLocations() {
        return locationRepository.getAll();
    }

    @GetMapping("/{id}")
    private Location getLocationById(@PathVariable Long id) {
        return locationRepository.getById(id);
    }

    @PostMapping()
    private Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @PutMapping("/{id}")
    private Location updateLocationById(@PathVariable Long id, @RequestBody Location newLocationData) {
        Location location = getLocationById(id);
        location.setCoords(newLocationData.getCoords());
        location.setSlug(newLocationData.getSlug());
        location.setName(newLocationData.getName());
        location.setTimezone(newLocationData.getTimezone());
        location.setCurrency(newLocationData.getCurrency());
        location.setLanguage(newLocationData.getLanguage());

        return location;
    }

    @DeleteMapping("/{id}")
    private void deleteLocationById(@PathVariable Long id) {
        locationRepository.deleteById(id);
    }
}
