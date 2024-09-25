package tbank.kudago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class KudaGoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(KudaGoSpringApplication.class, args);
    }
}
