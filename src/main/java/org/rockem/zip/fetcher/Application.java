package org.rockem.zip.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/zipcode")
    public ResponseEntity<ZipCode> zip(@RequestParam("bounds") String bounds) {
        return ResponseEntity.badRequest().build();
    }
}
