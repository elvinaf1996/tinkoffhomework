package tbank.kudago.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.kudago.controller.LocationController;
import tbank.kudago.model.Location;
import tbank.kudago.repository.LocationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepository locationRepository;

    private final String API_URL = "/api/v1/locations";

    @Test
    public void successGetAllLocations() throws Exception {
        List<Location> locations = Arrays.asList(
                new Location("Location slug1", "Location name1"),
                new Location("Location slug2", "Location name2")
        );
        Mockito.when(locationRepository.getAll()).thenReturn(locations);

        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(locations.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(locations.get(1).getName()))
                .andExpect(jsonPath("$[0].slug").value(locations.get(0).getSlug()))
                .andExpect(jsonPath("$[1].slug").value(locations.get(1).getSlug()));
    }

    @Test
    public void successGetLocationById() throws Exception {
        Long id = 1L;
        Location location = new Location("Location slug1", "Location name1");
        when(locationRepository.getById(id)).thenReturn(Optional.of(location));
        mockMvc.perform(get(API_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.slug").value(location.getSlug()));

        verify(locationRepository, times(1)).getById(id);
    }

    @Test
    public void successCreateLocation() throws Exception {
        Location savedLocation = new Location("New Location slug", "New Location name");
        when(locationRepository.save(any())).thenReturn(savedLocation);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(savedLocation);
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(savedLocation.getName()));

        verify(locationRepository, times(1)).save(any());
    }

    @Test
    public void successUpdateLocationById() throws Exception {
        Long id = 1L;
        Location existingLocation = new Location("Old Location slug", "Old Location name");
        Location updatedLocationData = new Location("Updated Location slug", "Updated Location name");
        when(locationRepository.getById(id)).thenReturn(Optional.of(existingLocation));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updatedLocationData);

        mockMvc.perform(put(API_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(updatedLocationData.getName()));

        verify(locationRepository, times(1)).getById(id);
    }

    @Test
    public void successDeleteLocationById() throws Exception {
        Long id = 1L;
        doNothing().when(locationRepository).deleteById(id);
        mockMvc.perform(delete(API_URL + "/{id}", id))
                .andExpect(status().isOk());
        verify(locationRepository, times(1)).deleteById(id);
    }

    @Test
    public void failureGetLocationById() throws Exception {
        when(locationRepository.getById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get(API_URL + "//{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failurePutLocationById() throws Exception {
        when(locationRepository.getById(1L)).thenReturn(Optional.empty());

        Location location = new Location("slug", "name");

        mockMvc.perform(put(API_URL + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isNotFound());
    }
}
