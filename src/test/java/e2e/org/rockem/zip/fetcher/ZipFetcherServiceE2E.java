package e2e.org.rockem.zip.fetcher;

import org.junit.Test;

import static e2e.org.rockem.zip.fetcher.GoogleMapsStub.aZeroResult;

public class ZipFetcherServiceE2E {

    private static final double[] NON_VALID_LOCATION = new double[] {1, 0};

    private final AppDriver application = new AppDriver();
    private final GoogleMapsStub googleMaps = new GoogleMapsStub();

    @Test
    public void failToFetchZipcodeOnNonValidLocation() throws Exception {
        googleMaps.given(NON_VALID_LOCATION).willReturn(aZeroResult());

        application.receiveTheLocation(NON_VALID_LOCATION);
        googleMaps.hasReceivedLocation(NON_VALID_LOCATION);
        application.retrieveUserError();
    }

}
