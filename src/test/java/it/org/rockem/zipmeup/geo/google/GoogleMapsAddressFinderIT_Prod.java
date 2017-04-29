package it.org.rockem.zipmeup.geo.google;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rockem.zipmeup.geo.Address;
import org.rockem.zipmeup.geo.google.GoogleMapsApi;
import org.rockem.zipmeup.geo.google.GoogleMapsAddressFinder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class GoogleMapsAddressFinderIT_Prod {

    private static final Address ADDRESS = Address.builder()
            .city("אזור")
            .street("קפלן")
            .houseNumber("16")
            .build();

    private final GoogleMapsAddressFinder finder =
            new GoogleMapsAddressFinder(GoogleMapsApi.createForIT());

    @Test
    public void shouldRetrieveAddressForLocation() throws Exception {
        assertThat(finder.find("32.0212,34.812"), is(ADDRESS));
    }
}