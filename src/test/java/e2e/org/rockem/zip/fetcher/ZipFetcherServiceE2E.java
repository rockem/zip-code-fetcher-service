package e2e.org.rockem.zip.fetcher;

import org.junit.Test;

import static e2e.org.rockem.zip.fetcher.GoogleMapsStub.invalidRequest;

public class ZipFetcherServiceE2E {

    private static final String INVALID_LOCATION = "100,100";

    private final AppDriver application = new AppDriver();
    private final GoogleMapsStub googleMaps = new GoogleMapsStub();

    @Test
    public void failToFetchZipcodeOnNonValidLocation() throws Exception {
        googleMaps.given(INVALID_LOCATION).willReturn(invalidRequest());

        application.receiveTheLocation(INVALID_LOCATION);
        googleMaps.hasReceivedLocation(INVALID_LOCATION);
        application.retrieveUserError();
    }

}
