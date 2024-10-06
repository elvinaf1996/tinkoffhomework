package tbank.kudago.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.kudago.model.Category;
import tbank.kudago.model.Location;
import tbank.kudago.repository.LocationRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tbank.kudago.controller.AssertionsUtils.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private LocationRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String API_URL = "/api/v1/locations";

    @Test
    public void successCategoriesSizeMoreThanNull() throws Exception {
        List<Location> locationList = getLocationList();
        assertListNotEmpty(locationList, "Список локаций не инициализирован");
    }

    @Test
    public void successAddCategory() throws Exception {
        List<Location> locationList = getLocationList();
        int sizeBeforeAdding = locationList.size();
        Location location = new Location("New Slug", "New Name");
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isOk());
        List<Location> locationListAfterAdding = getLocationList();
        int sizeAfterAdding = locationListAfterAdding.size();
        assertIsTrue(sizeAfterAdding - sizeBeforeAdding == 1, "Категория не добавлена");
        Location lastLocation = locationListAfterAdding.get(sizeAfterAdding - 1);
        assertIsEquals(lastLocation.getName(), location.getName(), "name category is not equals");
        assertIsEquals(lastLocation.getSlug(), location.getSlug(), "slug category is not equals");
    }

    @Test
    public void successPutCategoryById() throws Exception {
        List<Location> locationList = getLocationList();
        List<Long> indexes = new ArrayList<>();
        for (Location location : locationList) {
            indexes.add(location.getId());
        }
        int randomIndexIndex = new Random().nextInt(indexes.size());
        long putIndex = indexes.get(randomIndexIndex);
        Location oldLocation = locationList.stream()
                .filter(l -> l.getId() == putIndex)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not found location with id " + putIndex));
        Category newCategory = new Category(oldLocation.getSlug().concat(" New"), oldLocation.getName().concat(" New"));
        mockMvc.perform(put(API_URL + "/" + putIndex)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk());
        Location locationListAfterPut = getLocationList()
                .stream()
                .filter(c -> c.getId() == putIndex)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(format("Category with id '%s' not found", putIndex)));
        assertIsEquals(locationListAfterPut.getName(), newCategory.getName(), "name category is not updated");
        assertIsEquals(locationListAfterPut.getSlug(), newCategory.getSlug(), "slug category is not updated");
    }

    @Test
    public void successDeleteById() throws Exception {
        List<Location> locationList = getLocationList();
        int putIndex = new Random().nextInt(locationList.size());
        mockMvc.perform(delete(API_URL + "/" + putIndex))
                .andExpect(status().isOk());
        mockMvc.perform(get(API_URL + "/" + putIndex))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failureGetCategoryById() throws Exception {
        List<Location> locationList = getLocationList();

        mockMvc.perform(get(API_URL + "/" + locationList.size() + 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failurePutCategoryById() throws Exception {
        List<Location> locationList = getLocationList();

        Category category = new Category("slug", "name");

        mockMvc.perform(put(API_URL + "/" + locationList.size() + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isNotFound());
    }

    private List<Location> getLocationList() throws Exception {
        String responseBeforeAdd = mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        Location[] locations = objectMapper.readValue(responseBeforeAdd, Location[].class);
        return Arrays.asList(locations);
    }
}
