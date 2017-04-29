package org.rockem.zipmeup;

import org.rockem.zipmeup.geo.AddressFinder;
import org.rockem.zipmeup.geo.ZipcodeFinder;

public class LocationResolver {
    private final AddressFinder addressFinder;
    private final ZipcodeFinder zipcodeFinder;

    public LocationResolver(AddressFinder addressFinder, ZipcodeFinder zipcodeFinder) {
        this.addressFinder = addressFinder;
        this.zipcodeFinder = zipcodeFinder;
    }

    public ZipCode resolve(String location) {
        return new ZipCode(zipcodeFinder.find(addressFinder.find(location)));
    }
}
