package it.org.rockem.zipmeup.geo.rest;

import org.junit.Test;
import org.rockem.zipmeup.geo.Address;
import org.rockem.zipmeup.geo.rest.IsraelZipcodeFinder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IsraelZipcodeFinderIT_Prod {

    private static final Address ADDRESS = Address.builder()
            .city("אזור")
            .street("אחד העם")
            .houseNumber("2")
            .build();

    private final IsraelZipcodeFinder zipcodeFinder = new IsraelZipcodeFinder("http://www.israelpost.co.il");

    @Test
    public void retrieveZipcode() throws Exception {
        assertThat(zipcodeFinder.find(ADDRESS), is("5801508"));

    }
}