package org.rockem.zip.fetcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Slf4j
@RestController
@SpringBootApplication
public class Application {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleMapsApi googleMapsApi;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }



    @Autowired
    public Application(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
    }


    @RequestMapping("/zipcode")
    public ResponseEntity<ZipCode> zip(@RequestParam("bounds") String bounds) throws UnsupportedEncodingException {
        ResponseEntity<String> r = restTemplate.getForEntity(
                createURIWith(bounds), String.class);
        log.debug("Result from google maps is:\n" + r.getBody());
        return ResponseEntity.badRequest().build();
    }

    private URI createURIWith(String bounds) {
        return UriComponentsBuilder.fromHttpUrl(googleMapsApi.getUrl() + "/maps/api/geocode/json")
        .queryParam("bounds", bounds).queryParam("key", googleMapsApi.getKey()).build().encode().toUri();
    }

}
