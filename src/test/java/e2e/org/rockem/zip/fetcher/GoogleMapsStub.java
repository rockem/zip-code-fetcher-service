package e2e.org.rockem.zip.fetcher;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static e2e.org.rockem.zip.fetcher.MapUtil.entry;
import static e2e.org.rockem.zip.fetcher.MapUtil.map;

public class GoogleMapsStub {

    private double[] location;

    private final WireMockServer wm = new WireMockServer(options().port(2345));

    public GoogleMapsStub() {
        wm.start();
        configureFor("localhost", 2345);
    }

    public GoogleMapsStub receiveLocation(double[] location) {
        this.location = location;
        return this;
    }

    public void andFail() {
        stubFor(get(
                urlEqualTo("/maps/api/geocode/json?bounds=" + location[0] + "," + location[1]))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(new Gson().toJson(map(entry("status", "ZERO_RESULTS"))))));
    }
}
