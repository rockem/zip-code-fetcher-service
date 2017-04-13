package e2e.org.rockem.zip.fetcher;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.google.gson.Gson;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static e2e.org.rockem.zip.fetcher.MapUtil.entry;
import static e2e.org.rockem.zip.fetcher.MapUtil.map;

public class GoogleMapsStub {

    public static final String BASE_PATH = "/maps/api/geocode/json.*";
    private String location;

    public GoogleMapsStub() {
        new WireMockServer(options().port(2345)).start();
        configureFor("localhost", 2345);
    }

    public GoogleMapsStub given(String location) {
        this.location = location;
        return this;
    }

    public void willReturn(ResponseDefinitionBuilder response) {
        stubFor(get(
                urlMatching(BASE_PATH))
                .withQueryParam("latlng", equalTo(location))
                .withQueryParam("key", equalTo("SECRET_TOKEN"))
                .willReturn(response));
    }

    public static ResponseDefinitionBuilder invalidRequest() {
        return aResponse()
                .withStatus(400)
                .withBody(new Gson().toJson(map(entry("status", "INVALID_REQUEST"))));
    }

    public void hasReceivedLocation(String location) {

        verify(getRequestedFor(urlMatching("/maps/api/geocode/json.*"))
                .withQueryParam("latlng", equalTo(location))
                .withQueryParam("key", equalTo("SECRET_TOKEN")));
    }
}
