package tbank.homework5.controller;

import org.springframework.web.bind.annotation.*;
import tbank.homework5.model.Category;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1/places/categories")
public class CategoryController {

    private List<Category> categories = new ArrayList<>();
    private Long currentId = 1L;

    @GetMapping
    public List<Category> getAllCategories() {
        return categories;
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable String id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(format("Not found category with id '%s'", id)));
    }

    @PostMapping()
    public Category addCategory(@RequestBody Category category) {
        category.setId(Long.toString(++currentId));
        categories.add(category);

        return category;
    }

    @PutMapping("/{id}")
    public Category putCategoryById(@PathVariable String id, @RequestBody Category newCategoryData) {
        Category category = getCategoryById(id);
        category.setName(newCategoryData.getName());
        category.setSlug(newCategoryData.getSlug());

        return category;
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable String id) {
        categories.removeIf(c -> c.getId().equals(id));
    }
}
