package test.org.rockem.zipmeup.geo;

import org.junit.Test;
import org.rockem.zipmeup.geo.Address;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AddressTest {

    @Test
    public void retrieveFirstInRangeHouseNumber() throws Exception {
        Address address = Address.builder().houseNumber("11-13").build();
        assertThat(address.getHouseNumber(), is("11"));
    }
}