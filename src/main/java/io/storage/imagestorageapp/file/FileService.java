package io.storage.imagestorageapp.file;

import com.amazonaws.services.s3.model.S3Object;

/**
 * Author: Samandar_Akbarov
 * Date: 10/27/2022
 */
public interface FileService {
    byte[] readBitmap(S3Object s3Object);

}
