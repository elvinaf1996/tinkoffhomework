package tbank.kudago.utils;

import tbank.kudago.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore<T extends Model> {
    private Map<Long, T> store = new ConcurrentHashMap<>();
    private Long currentId = 1L;

    public T save(T item) {
        item.setId(currentId);
        return store.put(currentId++, item);
    }

    public T getById(Long id) {
        return store.get(id);
    }

    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}