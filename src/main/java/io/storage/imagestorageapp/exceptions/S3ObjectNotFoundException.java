package io.storage.imagestorageapp.exceptions;

/**
 * Author: Samandar_Akbarov
 * Date: 10/27/2022
 */
public class S3ObjectNotFoundException extends RuntimeException {
    public S3ObjectNotFoundException() {
        super("This object is not exist in bucket");
    }
}
