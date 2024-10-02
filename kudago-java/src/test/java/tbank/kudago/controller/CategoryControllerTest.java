package tbank.kudago.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tbank.kudago.model.Category;
import tbank.kudago.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryStore;

    private final String API_URL = "/api/v1/places/categories";

    @Test
    public void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category("Slug1", "Name1"),
                new Category("Slug2", "Name2")
        );
        Mockito.when(categoryStore.getAll()).thenReturn(categories);

        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Name1"))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[0].slug").value("Slug1"))
                .andExpect(jsonPath("$[1].slug").value("Slug2"));
    }

    @Test
    public void getCategoryById() throws Exception {
        Category category = new Category("Slug1", "Name1");
        when(categoryStore.getById(1L)).thenReturn(category);

        mockMvc.perform(get(API_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.slug").value("Slug1"));
    }

    @Test
    public void addCategory() throws Exception {
        Category newCategory = new Category("Slug1", "Name1");
        when(categoryStore.save(any(Category.class))).thenReturn(newCategory);

        mockMvc.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Name1\", \"slug\": \"Slug1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Name1"));
    }

    @Test
    public void putCategoryById_ShouldUpdateCategory() throws Exception {
        long categoryId = 1L;
        Category existingCategory = new Category("Old Slug", "Old Name");
        when(categoryStore.getById(categoryId)).thenReturn(existingCategory);

        Category newCategoryData = new Category("New Slug", "New Name");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newCategoryData);

        mockMvc.perform(put(API_URL + "/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.slug").value("New Slug"));

        verify(categoryStore, times(1)).getById(categoryId);
    }

    @Test
    public void deleteCategoryById() throws Exception {
        doNothing().when(categoryStore).deleteById(1L);

        mockMvc.perform(delete(API_URL + "/1"))
                .andExpect(status().isOk());

        verify(categoryStore, times(1)).deleteById(1L);
    }
}
