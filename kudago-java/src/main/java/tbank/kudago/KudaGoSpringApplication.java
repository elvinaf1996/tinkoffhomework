package tbank.kudago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class KudaGoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(KudaGoSpringApplication.class, args);
    }
}
