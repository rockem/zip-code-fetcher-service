package e2e.org.rockem.zip.fetcher;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import e2e.org.rockem.zip.fetcher.support.Address;
import e2e.org.rockem.zip.fetcher.support.AppDriver;
import e2e.org.rockem.zip.fetcher.support.stub.GoogleMapsStub;
import e2e.org.rockem.zip.fetcher.support.stub.IsraelPostStub;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static e2e.org.rockem.zip.fetcher.support.Address.locationOf;
import static e2e.org.rockem.zip.fetcher.support.Address.zipcodeOf;

public class ZipFetcherServiceE2E {

    private static final String INVALID_LOCATION = "100,100";
    private static final Address ADDRESS_IN_ISRAEL = Address.builder()
            .country("ישראל")
            .city("רמת גן")
            .street("נורדאו")
            .houseNumber("3")
            .location("32.0842,34.8124")
            .zipcode("5246415").build();

    private static AppDriver application;
    private static GoogleMapsStub googleMaps;
    private static IsraelPostStub israeliPost;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(3456));

    @BeforeClass
    public static void startApp() throws Exception {
        application = new AppDriver();
    }

    @BeforeClass
    public static void startStubs() throws Exception {
        googleMaps = new GoogleMapsStub();
        israeliPost = new IsraelPostStub();
    }

    @Test
    public void failToFetchZipcodeOnNonValidLocation() throws Exception {
        application.receiveTheLocation(INVALID_LOCATION);
        googleMaps.hasReceivedLocation(INVALID_LOCATION);

        application.retrieveUserError();
    }

    @Test
    public void retrieveIsraeliZipcode() throws Exception {
        application.receiveTheLocation(locationOf(ADDRESS_IN_ISRAEL));
        googleMaps.hasReceivedLocation(locationOf(ADDRESS_IN_ISRAEL));
        israeliPost.hasReceivedAddress(ADDRESS_IN_ISRAEL);

        application.retrieve(zipcodeOf(ADDRESS_IN_ISRAEL));

    }

}
