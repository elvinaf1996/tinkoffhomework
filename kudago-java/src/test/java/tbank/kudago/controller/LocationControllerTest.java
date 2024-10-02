package tbank.kudago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.kudago.model.Location;
import tbank.kudago.repository.LocationRepository;

import java.util.Arrays;
import java.util.List;

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
    public void testGetAllLocations() throws Exception {
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
    public void testGetLocationById() throws Exception {
        Long id = 1L;
        Location location = new Location("Location slug1", "Location name1");
        when(locationRepository.getById(id)).thenReturn(location);
        mockMvc.perform(get(API_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.slug").value(location.getSlug()));

        verify(locationRepository, times(1)).getById(id);
    }

    @Test
    public void testCreateLocation() throws Exception {
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
    public void testUpdateLocationById() throws Exception {
        Long id = 1L;
        Location existingLocation = new Location("Old Location slug", "Old Location name");
        Location updatedLocationData = new Location("Updated Location slug", "Updated Location name");
        when(locationRepository.getById(id)).thenReturn(existingLocation);
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
    public void testDeleteLocationById() throws Exception {
        Long id = 1L;
        doNothing().when(locationRepository).deleteById(id);
        mockMvc.perform(delete(API_URL + "/{id}", id))
                .andExpect(status().isOk());
        verify(locationRepository, times(1)).deleteById(id);
    }
}
