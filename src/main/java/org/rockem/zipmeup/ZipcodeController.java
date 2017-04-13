package org.rockem.zipmeup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class ZipcodeController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleMapsApi googleMapsApi;


    @Autowired
    public ZipcodeController(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        restTemplate.setErrorHandler(new MyErrorHandler());
    }

    @RequestMapping(value = "/zipcode", method = RequestMethod.GET)
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
