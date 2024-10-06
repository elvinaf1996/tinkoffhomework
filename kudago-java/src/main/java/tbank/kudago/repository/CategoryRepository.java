package tbank.kudago.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tbank.kudago.model.Category;
import tbank.kudago.utils.DataStore;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final DataStore<Category> categoryStore;

    public Category save(Category category) {
        return categoryStore.save(category);
    }

    public List<Category> getAll() {
        return categoryStore.getAll();
    }

    public Optional<Category> getById(Long id) {
        return Optional.ofNullable(categoryStore.getById(id));
    }

    public void deleteById(Long id) {
        categoryStore.deleteById(id);
    }
}
