package org.rockem.zipmeup.geo;

public interface AddressFinder {
    Address find(String location);

    class LocationNotValidException extends RuntimeException {
    }
}
