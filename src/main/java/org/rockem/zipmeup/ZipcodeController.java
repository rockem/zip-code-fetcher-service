package org.rockem.zipmeup;

import lombok.extern.slf4j.Slf4j;
import org.rockem.zipmeup.geo.AddressFinder;
import org.rockem.zipmeup.geo.ZipcodeFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ZipcodeController {

    private AddressFinder addressFinder;
    private ZipcodeFinder zipcodeFinder;

    @Autowired
    public ZipcodeController(AddressFinder addressFinder, ZipcodeFinder zipcodeFinder) {
        this.addressFinder = addressFinder;
        this.zipcodeFinder = zipcodeFinder;
    }

    @RequestMapping(value = "/zipcode", method = RequestMethod.GET)
    public ResponseEntity<ZipCode> zip(@RequestParam("location") String location) {
        return ResponseEntity.ok(new LocationResolver(addressFinder, zipcodeFinder).resolve(location));
    }

}
