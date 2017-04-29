package org.rockem.zipmeup.geo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.rockem.zipmeup.geo.google.AddressGoogleAPIDeserializer;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Address {

    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String zipcode;

    public static Address fromJson(String json, Object adapter) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, adapter).create();
        return gson.fromJson(json, Address.class);
    }
}
