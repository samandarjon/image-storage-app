package io.storage.imagestorageapp.utils;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
public interface ImageUtil {
    static String extractExtension(String fileName) {
        return fileName.substring(fileName.indexOf("."));
    }
}
