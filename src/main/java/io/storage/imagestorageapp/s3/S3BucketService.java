package io.storage.imagestorageapp.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import io.storage.imagestorageapp.exceptions.S3ObjectNotFoundException;
import io.storage.imagestorageapp.file.FileService;
import io.storage.imagestorageapp.image.Image;
import io.storage.imagestorageapp.image.ImageRepository;
import io.storage.imagestorageapp.utils.ImageUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static io.storage.imagestorageapp.constants.AwsConst.S3_BUCKET_NAME;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
@Service
public class S3BucketService {
    private final AmazonS3 s3;
    private final FileService fileService;
    private final ImageRepository imageRepository;


    public S3BucketService(AmazonS3 s3, FileService fileService, ImageRepository imageRepository) {
        this.s3 = s3;
        this.fileService = fileService;
        this.imageRepository = imageRepository;
    }

    public HttpEntity<byte[]> downloadObject(Long imageId) {
        Image image = imageRepository.findByIdAndIsActiveTrue(imageId).orElseThrow(S3ObjectNotFoundException::new);
        checkBucketExists();
        checkObjectExits(image.getName());

        S3Object file = s3.getObject(S3_BUCKET_NAME, image.getName());
        String contentType = file.getObjectMetadata().getContentType();
        byte[] bytes = fileService.readBitmap(file);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(contentType));
        header.setContentLength(bytes.length);
        header.add("content-disposition", "attachment; filename=\"" + image.getName() + "\"");

        return new HttpEntity<>(bytes, header);
    }

    @Transactional
    public void uploadObject(Long imageId,
                             MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        //validations
        checkBucketExists();
        //update image data form db
        updateImageData(imageId, file, filename);

        //saving image to s3 bucket
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("Name", filename);
        metadata.setContentType("image/jpg");
        try {
            PutObjectRequest request = new PutObjectRequest(S3_BUCKET_NAME, filename, file.getInputStream(), metadata);
            request.setMetadata(metadata);
            PutObjectResult putObjectResult = s3.putObject(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateImageData(Long imageId, MultipartFile file, String filename) {
        Image image = imageRepository.findById(imageId).orElseThrow(S3ObjectNotFoundException::new);
        image.setSize(file.getSize());
        image.setName(filename);
        image.setExtension(ImageUtil.extractExtension(filename));
        image.setActive(true);
        imageRepository.save(image);
    }

    public void deleteObject(String objectName) {
        checkBucketExists();
        checkObjectExits(objectName);
        s3.deleteObject(S3_BUCKET_NAME, objectName);
    }

    private void checkBucketExists() {
        if (!s3.doesBucketExistV2(S3_BUCKET_NAME)) {
            s3.createBucket(S3_BUCKET_NAME);
        }
    }

    private void checkObjectExits(String objectName) {
        if (!s3.doesObjectExist(S3_BUCKET_NAME, objectName)) {
            throw new S3ObjectNotFoundException();
        }
    }
}
