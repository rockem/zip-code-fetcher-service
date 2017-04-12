package e2e.org.rockem.zip.fetcher;

import org.junit.Test;

import static e2e.org.rockem.zip.fetcher.GoogleMapsStub.aZeroResult;

public class ZipFetcherServiceE2E {

    private static final double[] UNKNOWN_LOCATION = new double[]{1, 0, 1, 0};

    private final AppDriver application = new AppDriver();
    private final GoogleMapsStub googleMaps = new GoogleMapsStub();

    @Test
    public void failToFetchZipcodeOnNonValidLocation() throws Exception {
        googleMaps.given(UNKNOWN_LOCATION).willReturn(aZeroResult());

        application.receiveTheLocation(UNKNOWN_LOCATION);
        googleMaps.hasReceivedLocation(UNKNOWN_LOCATION);
        application.retrieveUserError();
    }

}
