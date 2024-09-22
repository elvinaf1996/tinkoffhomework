package tbank.kudago.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import felv.logger.LogExecutionTime;
import tbank.kudago.model.Category;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final DataStore<Category> categoryStore;
    private final DataStore<Location> locationStore;

    @Override
    @LogExecutionTime
    public void run(String... args) {
        logger.info("Starting data initialization...");
        initializeCategoryStore();
        initializeLocationStore();
        logger.info("Initialization of categories and cities is complete.");
    }

    private void initializeCategoryStore() {
        logger.info("Starting initialization of data for categories...");
        String url = "https://kudago.com/public-api/v1.2/place-categories/?lang=ru&fields=name,slug";

        Category[] categoriesMass = restTemplate.getForObject(url, Category[].class);
        if (nonNull(categoriesMass) && categoriesMass.length != 0) {
            Arrays.asList(categoriesMass).forEach(categoryStore::save);
        }
        logger.info("Category initialization completed.");
    }

    private void initializeLocationStore() {
        logger.info("Start of initialization of data for cities...");
        String url = "https://kudago.com/public-api/v1.2/locations/?lang=ru";

        Location[] categoriesMass = restTemplate.getForObject(url, Location[].class);
        if (nonNull(categoriesMass) && categoriesMass.length != 0) {
            Arrays.asList(categoriesMass).forEach(locationStore::save);
        }
        logger.info("Cities initialization completed.");
    }
}
