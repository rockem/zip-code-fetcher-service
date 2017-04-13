package org.rockem.zipmeup;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="googleMaps.api")
@Getter
@Setter
@ToString
public class GoogleMapsApi {

    private String url;
    private String key;
}
