package tbank.kudago.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final DataStore<Location> locationStore;

    public Location save(Location location) {
        return locationStore.save(location);
    }

    public List<Location> getAll() {
        return locationStore.getAll();
    }

    public Optional<Location> getById(Long id) {
        return Optional.ofNullable(locationStore.getById(id));
    }

    public void deleteById(Long id) {
        locationStore.deleteById(id);
    }
}
