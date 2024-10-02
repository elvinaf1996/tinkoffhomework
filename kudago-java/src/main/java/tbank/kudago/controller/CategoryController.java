package tbank.kudago.controller;

import felv.logger.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tbank.kudago.model.Category;
import tbank.kudago.repository.CategoryRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places/categories")
@LogExecutionTime
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryRepository.getById(id);
    }

    @PostMapping()
    public Category addCategory(@RequestBody Category category) {
        categoryRepository.save(category);

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
        categoryRepository.deleteById(id);
    }
}
