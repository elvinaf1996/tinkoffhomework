package tbank.kudago.components;

import felv.logger.LogExecutionTime;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tbank.kudago.model.Category;
import tbank.kudago.model.Location;
import tbank.kudago.repository.CategoryRepository;
import tbank.kudago.repository.LocationRepository;

import java.util.Arrays;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final RestTemplate restTemplate;
    private final CategoryRepository categoryStore;
    private final LocationRepository locationStore;
    private final String LOCATION_URL = "https://kudago.com/public-api/v1.2/locations/?lang=ru&fields=slug,name,timezone,coords,language";
    private final String CATEGORY_URL = "https://kudago.com/public-api/v1.2/place-categories/?lang=ru&fields=name,slug";

    @PostConstruct
    public void run() {
        logger.info("Starting data initialization...");
        initializeCategoryStore();
        initializeLocationStore();
        logger.info("Initialization of categories and cities is complete.");
    }

    @LogExecutionTime
    private void initializeCategoryStore() {
        logger.info("Starting initialization of data for categories...");

        Category[] categoriesMass = restTemplate.getForObject(CATEGORY_URL, Category[].class);
        if (nonNull(categoriesMass) && categoriesMass.length != 0) {
            Arrays.asList(categoriesMass).forEach(categoryStore::save);
        }
        logger.info("Category initialization completed.");
    }

    @LogExecutionTime
    private void initializeLocationStore() {
        logger.info("Start of initialization of data for cities...");

        Location[] categoriesMass = restTemplate.getForObject(LOCATION_URL, Location[].class);
        if (nonNull(categoriesMass) && categoriesMass.length != 0) {
            Arrays.asList(categoriesMass).forEach(locationStore::save);
        }
        logger.info("Cities initialization completed.");
    }
}
