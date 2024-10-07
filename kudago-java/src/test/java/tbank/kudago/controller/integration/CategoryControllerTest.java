package tbank.kudago.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.kudago.model.Category;
import tbank.kudago.repository.CategoryRepository;

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
public class CategoryControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String API_URL = "/api/v1/places/categories";

    @Test
    public void successCategoriesSizeMoreThanNull() throws Exception {
        List<Category> categoryList = getCategoryList();
        assertListNotEmpty(categoryList, "Список категорий не инициализирован");
    }

    @Test
    public void successAddCategory() throws Exception {
        List<Category> categoryList = getCategoryList();
        int sizeBeforeAdding = categoryList.size();
        Category category = new Category("New Slug", "New Name");
        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk());
        List<Category> categoryListAfterAdding = getCategoryList();
        int sizeAfterAdding = categoryListAfterAdding.size();
        assertIsTrue(sizeAfterAdding - sizeBeforeAdding == 1, "Категория не добавлена");
        Category lastCategory = categoryListAfterAdding.get(sizeAfterAdding - 1);
        assertIsEquals(lastCategory.getName(), category.getName(), "name category is not equals");
        assertIsEquals(lastCategory.getSlug(), category.getSlug(), "slug category is not equals");
    }

    @Test
    public void successPutCategoryById() throws Exception {
        List<Category> categoryList = getCategoryList();
        List<Long> indexes = new ArrayList<>();
        for (Category category : categoryList) {
            indexes.add(category.getId());
        }
        int randomIndexIndex = new Random().nextInt(indexes.size());
        long putIndex = indexes.get(randomIndexIndex);
        Category oldCategory = categoryList.stream()
                .filter(l -> l.getId() == putIndex)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not found location with id " + putIndex));
        Category newCategory = new Category(oldCategory.getSlug().concat(" New"), oldCategory.getName().concat(" New"));
        mockMvc.perform(put(API_URL + "/{id}", putIndex)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk());
        Category categoryListAfterPut = getCategoryList()
                .stream()
                .filter(c -> c.getId() == putIndex)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(format("Category with id '%s' not found", putIndex)));
        assertIsEquals(categoryListAfterPut.getName(), newCategory.getName(), "name category is not updated");
        assertIsEquals(categoryListAfterPut.getSlug(), newCategory.getSlug(), "slug category is not updated");
    }

    @Test
    public void successDeleteById() throws Exception {
        List<Category> categoryList = getCategoryList();
        int putIndex = new Random().nextInt(categoryList.size());
        mockMvc.perform(delete(API_URL + "/{id}", putIndex))
                .andExpect(status().isOk());
        mockMvc.perform(get(API_URL + "/{id}", putIndex))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failureGetCategoryById() throws Exception {
        List<Category> categoryList = getCategoryList();

        mockMvc.perform(get(API_URL + "/{id}", categoryList.size() + 10))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failurePutCategoryById() throws Exception {
        List<Category> categoryList = getCategoryList();

        Category category = new Category("slug", "name");

        mockMvc.perform(put(API_URL + "/{id}", categoryList.size() + 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(status().isNotFound());
    }

    private List<Category> getCategoryList() throws Exception {
        String responseBeforeAdd = mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        Category[] categories = objectMapper.readValue(responseBeforeAdd, Category[].class);
        return Arrays.asList(categories);
    }
}
