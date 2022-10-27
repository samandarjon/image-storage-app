package io.storage.imagestorageapp.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Author: Samandar_Akbarov
 * Date: 10/27/2022
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public byte[] readBitmap(S3Object s3Object) {
        byte[] objectBytes = new byte[1024];
        try (S3ObjectInputStream s3is = s3Object.getObjectContent();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            byte[] read_buf = new byte[1024];
            int read_len;
            while ((read_len = s3is.read(read_buf)) > 0) {
                os.write(read_buf, 0, read_len);
            }

            objectBytes = os.toByteArray();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return objectBytes;
    }
}
