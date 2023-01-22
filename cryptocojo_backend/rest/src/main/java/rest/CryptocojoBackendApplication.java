package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptocojoBackendApplication {

    /**
     *
     * private constructor for utility class.
     * this creates an error when launching springboot
     */
    // private CryptocojoBackendApplication() {
    // }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(CryptocojoBackendApplication.class, args);
    }

}
