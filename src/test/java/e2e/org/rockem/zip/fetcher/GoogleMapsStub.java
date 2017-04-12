package e2e.org.rockem.zip.fetcher;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static e2e.org.rockem.zip.fetcher.MapUtil.entry;
import static e2e.org.rockem.zip.fetcher.MapUtil.map;

public class GoogleMapsStub {

    private double[] location;

    public GoogleMapsStub() {
        new WireMockServer(options().port(2345)).start();
        configureFor("localhost", 2345);
    }

    public GoogleMapsStub given(double[] location) {
        this.location = location;
        return this;
    }

    public void willReturn(String result) {
        stubFor(get(
                urlMatching("/maps/api/geocode/json.*"))
                .withQueryParam("bounds", equalTo(boundsValue(location)))
                .withQueryParam("key", equalTo("SECRET_TOKEN"))
                .willReturn(aResponse().withBody(result)));
    }

    private String boundsValue(double[] location) {
        return location[0] + "," + location[1] + "|" + location[2] + "," + location[3];
    }

    public static String aZeroResult() {
        return new Gson().toJson(map(entry("status", "ZERO_RESULTS")));
    }

    public void hasReceivedLocation(double[] location) {

        verify(getRequestedFor(urlMatching("/maps/api/geocode/json.*")).withQueryParam("bounds", equalTo(boundsValue(location))));
    }
}
