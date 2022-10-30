package io.storage.imagestorageapp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
public interface LambdaMethods {
    static RuntimeException throwable(Throwable e) {

        return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
}
