package tbank.kudago.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tbank.kudago.model.Category;
import tbank.kudago.model.Location;
import tbank.kudago.utils.DataStore;

@Configuration
@ComponentScan(basePackages = {"tbank.kudago", "felv.logger"})
public class KudaGoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier("categoryStore")
    public DataStore<Category> categoryStore() {
        return new DataStore<>();
    }

    @Bean
    @Qualifier("locationStore")
    public DataStore<Location> locationStore() {
        return new DataStore<>();
    }
}
