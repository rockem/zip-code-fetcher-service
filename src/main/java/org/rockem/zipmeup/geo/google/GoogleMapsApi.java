package org.rockem.zipmeup.geo.google;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="googleMaps.api")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleMapsApi {

    private String url;
    private String key;

    public static GoogleMapsApi createForIT() {
        return new GoogleMapsApi("https://maps.googleapis.com", "AIzaSyDw21XdTevOsWcHuWPcUvpOUJqh74Tdvz4");
    }

}
