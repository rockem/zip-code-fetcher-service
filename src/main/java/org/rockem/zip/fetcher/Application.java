package org.rockem.zip.fetcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
        restTemplate.setErrorHandler(new MyErrorHandler());
    }


    @RequestMapping("/zipcode")
    public ResponseEntity<ZipCode> zip(@RequestParam("location") String location) throws UnsupportedEncodingException {
        ResponseEntity<String> r = restTemplate.getForEntity(createURIWith(location), String.class);
        log.debug("Result from google maps is:\n" + r.getBody());
        return ResponseEntity.badRequest().build();
    }

    private URI createURIWith(String location) {
        return UriComponentsBuilder.fromHttpUrl(googleMapsApi.getUrl() + "/maps/api/geocode/json")
        .queryParam("latlng", location).queryParam("key", googleMapsApi.getKey()).build().encode().toUri();
    }

    private class MyErrorHandler implements ResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // your error handling here
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }
    }

}
