package test.org.rockem.zipmeup;

import org.junit.Test;
import org.rockem.zipmeup.LocationResolver;
import org.rockem.zipmeup.ZipCode;
import org.rockem.zipmeup.geo.Address;
import org.rockem.zipmeup.geo.AddressFinder;
import org.rockem.zipmeup.geo.ZipcodeFinder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationResolverTest {

    private static final String LOCATION = "valid-location";
    private static final Address ADDRESS = Address.builder().build();
    private static final String ZIPCODE = "123-zip";

    private final AddressFinder addressFinder = mock(AddressFinder.class);
    private final ZipcodeFinder zipcodeFinder = mock(ZipcodeFinder.class);
    private final LocationResolver locationResolver = new LocationResolver(addressFinder, zipcodeFinder);

    @Test
    public void retrieveZipcodeForSpecificLocation() throws Exception {
        when(addressFinder.find(LOCATION)).thenReturn(ADDRESS);
        when(zipcodeFinder.find(ADDRESS)).thenReturn(ZIPCODE);
        assertThat(locationResolver.resolve(LOCATION), is(new ZipCode(ZIPCODE)));
    }
}