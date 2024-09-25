package tbank.kudago.controller;

import felv.logger.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tbank.kudago.model.Location;
import tbank.kudago.repository.LocationRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
@LogExecutionTime
public class LocationController {

    private final LocationRepository locationRepository;

    @LogExecutionTime
    @GetMapping
    public List<Location> getAllLocation() {
        return locationRepository.getAll();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationRepository.getById(id);
    }

    @PostMapping()
    public Location addLocation(@RequestBody Location location) {
        locationRepository.save(location);

        return location;
    }

    @PutMapping("/{id}")
    public Location putLocationById(@PathVariable Long id, @RequestBody Location newLocationData) {
        Location location = getLocationById(id);
        location.setName(newLocationData.getName());
        location.setSlug(newLocationData.getSlug());

        return location;
    }

    @DeleteMapping("/{id}")
    public void deleteLocationById(@PathVariable Long id) {
        locationRepository.deleteById(id);
    }
}
