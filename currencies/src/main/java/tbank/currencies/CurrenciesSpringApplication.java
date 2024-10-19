package tbank.currencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrenciesSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrenciesSpringApplication.class, args);
    }
}
