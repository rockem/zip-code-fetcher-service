package e2e.org.rockem.zip.fetcher;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.rockem.zip.fetcher.Application;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppDriver {

    public static final String APP_DOMAIN = "http://localhost:8080";

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private HttpResponse lastResponse;

    public AppDriver() {
        Application.main();
    }

    public void receiveTheLocation(String location) throws IOException {
        lastResponse = httpClient.execute(new HttpGet(APP_DOMAIN + "/zipcode?location=" + location));
    }

    public void retrieveUserError() {
        assertThat(lastResponse.getStatusLine().getStatusCode(), is(400));
    }
}
