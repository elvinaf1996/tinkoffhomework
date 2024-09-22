package tbank.kudago.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tbank.kudago.model.Category;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

@Configuration
public class KudaGoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataStore<Category> categoryDataStore() {
        return new DataStore<>();
    }

    @Bean
    public DataStore<Location> locationDataStore() {
        return new DataStore<>();
    }
}
