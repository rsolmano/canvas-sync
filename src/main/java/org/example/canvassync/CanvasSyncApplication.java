package org.example.canvassync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class CanvasSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanvasSyncApplication.class, args);
    }

}