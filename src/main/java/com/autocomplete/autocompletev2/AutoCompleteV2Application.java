package com.autocomplete.autocompletev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.autocomplete.autocompletev2.model")

public class AutoCompleteV2Application {

    public static void main(String[] args) {
        SpringApplication.run(AutoCompleteV2Application.class, args);
    }

}
