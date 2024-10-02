package tbank.kudago.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

import java.util.List;

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

    public Location getById(Long id) {
        return locationStore.getById(id);
    }

    public void deleteById(Long id) {
        locationStore.deleteById(id);
    }
}
