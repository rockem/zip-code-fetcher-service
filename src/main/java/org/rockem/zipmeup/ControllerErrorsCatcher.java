package org.rockem.zipmeup;

import org.rockem.zipmeup.geo.AddressFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerErrorsCatcher {

    @ExceptionHandler(value = {AddressFinder.LocationNotValidException.class })
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().build();
    }
}
