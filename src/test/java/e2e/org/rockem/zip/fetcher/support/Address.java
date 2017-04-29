package e2e.org.rockem.zip.fetcher.support;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Address {

    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String location;
    private String zipcode;

    public static String locationOf(Address address) {
        return address.location;
    }

    public static String zipcodeOf(Address address) {
        return address.zipcode;
    }
}