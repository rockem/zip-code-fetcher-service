package e2e.org.rockem.zip.fetcher.support.stub;

import e2e.org.rockem.zip.fetcher.support.Address;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class IsraelPostStub {

    private static final String SEARCH_ZIP_PATH = "/zip_data.nsf/SearchZip.*";

    public void hasReceivedAddress(Address address) {
        verify(getRequestedFor(urlMatching(SEARCH_ZIP_PATH))
                .withQueryParam("Location", equalTo(address.getCity()))
                .withQueryParam("Street", equalTo(address.getStreet()))
                .withQueryParam("House", equalTo(address.getHouseNumber())));
    }

    public static void main(String[] args) {
        new WiremockRecorder("http://www.israelpost.co.il").record();
    }
}
