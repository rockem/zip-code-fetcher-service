package e2e.org.rockem.zip.fetcher;

import org.junit.Test;

public class ZipFetcherServiceE2E {

    private static final double[] LOCATION = new double[] {1, 0};

    private final AppDriver application = new AppDriver();
    private final GoogleMapsStub googleMaps = new GoogleMapsStub();

    @Test
    public void failToFetchZipcodeOnNonValidLocation() throws Exception {
        application.receiveTheLocation(LOCATION);
        googleMaps.receiveLocation(LOCATION).andFail();
        application.retrieveUserError();
    }
}
