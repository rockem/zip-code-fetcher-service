package org.rockem.zipmeup.geo.google;

import lombok.extern.slf4j.Slf4j;
import org.rockem.zipmeup.geo.Address;
import org.rockem.zipmeup.geo.AddressFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Service
public class GoogleMapsAddressFinder implements AddressFinder {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleMapsApi googleMapsApi;

    @Autowired
    public GoogleMapsAddressFinder(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        restTemplate.setErrorHandler(new MyErrorHandler());
    }

    public Address find(String location) {
        ResponseEntity<String> result = restTemplate.getForEntity(createURIWith(location), String.class);
        log.debug("Result from google maps is:\n" + result.getBody());
        if(result.getStatusCodeValue() == 400) {
            throw new LocationNotValidException();
        }
        return Address.fromJson(result.getBody(), new AddressGoogleAPIDeserializer());
    }

    private URI createURIWith(String location) {
        return UriComponentsBuilder.fromHttpUrl(googleMapsApi.getUrl() + "/maps/api/geocode/json")
                .queryParam("latlng", location)
                .queryParam("key", googleMapsApi.getKey())
                .queryParam("language", "he")
                .build().encode().toUri();
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
