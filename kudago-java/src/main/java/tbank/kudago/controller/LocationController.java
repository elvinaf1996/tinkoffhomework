package tbank.kudago.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final DataStore<Location> locationStore;

    @GetMapping()
    private List<Location> getAllLocations() {
        return locationStore.getAll();
    }

    @GetMapping("/{id}")
    private Location getLocationById(@PathVariable Long id) {
        return locationStore.getById(id);
    }

    @PostMapping()
    private Location createLocation(@RequestBody Location location) {
        locationStore.save(location);

        return location;
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
        locationStore.deleteById(id);
    }
}
