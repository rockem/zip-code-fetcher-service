package e2e.org.rockem.zip.fetcher;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.rockem.zip.fetcher.Application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppDriver {

    public static final String APP_DOMAIN = "http://localhost:8080";

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private HttpResponse lastResponse;

    public AppDriver() {
        Application.main();
    }

    public void receiveTheLocation(double[] location) throws IOException {
        lastResponse = httpClient.execute(new HttpGet(APP_DOMAIN + "/zipcode?bounds=" + boundsValue(location)));
    }

    private String boundsValue(double[] location) {
        try {
            return URLEncoder.encode(location[0] + "," + location[1] + "|" + location[2] + "," + location[3], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void retrieveUserError() {
        assertThat(lastResponse.getStatusLine().getStatusCode(), is(400));
    }
}
