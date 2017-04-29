package org.rockem.zipmeup.geo.rest;

import lombok.extern.slf4j.Slf4j;
import org.rockem.zipmeup.geo.Address;
import org.rockem.zipmeup.geo.ZipcodeFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class IsraelZipcodeFinder implements ZipcodeFinder {

    public static final Pattern ZIPCODE_PATTERN = Pattern.compile("RES8(\\d+)");
    public static final String SEARCH_ZIP_PATH = "/zip_data.nsf/SearchZip";

    private final RestTemplate restTemplate = new RestTemplate();
    private final String israelPostUrl;

    public IsraelZipcodeFinder(@Value("${israelPost.url}") String israelPostUrl) {
        this.israelPostUrl = israelPostUrl;
    }

    public String find(Address address) {
        ResponseEntity<String> response = restTemplate.getForEntity(createURIWithAddress(address), String.class);
        return extractZipcodeFrom(response.getBody());
    }

    private String extractZipcodeFrom(String str) {
        Matcher m = ZIPCODE_PATTERN.matcher(str);
        m.find();
        return m.group(1);
    }

    private URI createURIWithAddress(Address address) {
        return UriComponentsBuilder.fromHttpUrl(israelPostUrl + SEARCH_ZIP_PATH)
                .query("OpenAgent")
                .queryParam("Location", address.getCity())
                .queryParam("Street", address.getStreet())
                .queryParam("House", address.getHouseNumber())
                .build().encode().toUri();
    }
}
