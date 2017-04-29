package e2e.org.rockem.zip.fetcher.support.stub;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.google.gson.Gson;
import e2e.org.rockem.zip.fetcher.support.Address;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static e2e.org.rockem.zip.fetcher.support.MapUtil.entry;
import static e2e.org.rockem.zip.fetcher.support.MapUtil.map;

public class GoogleMapsStub {

    private static final String BASE_PATH = "/maps/api/geocode/json.*";

    public static ResponseDefinitionBuilder invalidRequest() {
        return aResponse()
                .withStatus(400)
                .withBody(new Gson().toJson(map(entry("status", "INVALID_REQUEST"))));
    }

    public void hasReceivedLocation(String location) {
        verify(getRequestedFor(urlMatching(BASE_PATH))
                .withQueryParam("latlng", equalTo(location)));
    }

    public static ResponseDefinitionBuilder aResponseOf(Address address) {
        return aResponse()
                .withStatus(200)
                .withBody(new Gson().toJson(
                        map(entry("results", Arrays.asList(createAddressComponentsFor(address))),
                                entry("status", "OK"))));
    }

    private static Map<String, List<Map<String, Object>>> createAddressComponentsFor(Address address) {
        return map(
                entry("address_components", Arrays.asList(
                        createAddressComponent("country", address.getCountry()),
                        createAddressComponent("locality", address.getCity()),
                        createAddressComponent("route", address.getStreet()),
                        createAddressComponent("street_number", String.valueOf(address.getHouseNumber()))
                )));
    }

    private static Map<String, Object> createAddressComponent(String type, String value) {
        return map(
                entry("long_name", value),
                entry("types", Arrays.asList(type)));
    }

    public static void main(String[] args) {
        new WiremockRecorder("https://maps.googleapis.com").record();
    }

}
