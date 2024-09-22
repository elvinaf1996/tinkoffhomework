package tbank.kudago.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import felv.logger.LogExecutionTime;
import tbank.kudago.model.Category;
import tbank.kudago.utils.DataStore;

import java.util.List;

@RestController
@LogExecutionTime
@RequiredArgsConstructor
@RequestMapping("/api/v1/places/categories")
public class CategoryController {

    private final DataStore<Category> categoryStore;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryStore.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryStore.getById(id);
    }

    @PostMapping()
    public Category addCategory(@RequestBody Category category) {
        categoryStore.save(category);

        return category;
    }

    @PutMapping("/{id}")
    public Category putCategoryById(@PathVariable Long id, @RequestBody Category newCategoryData) {
        Category category = getCategoryById(id);
        category.setName(newCategoryData.getName());
        category.setSlug(newCategoryData.getSlug());

        return category;
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryStore.deleteById(id);
    }
}
