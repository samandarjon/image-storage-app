package io.storage.imagestorageapp.image;

import io.storage.imagestorageapp.s3.S3BucketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;
    private final S3BucketService s3BucketService;

    public ImageController(ImageService imageService, S3BucketService s3BucketService) {
        this.imageService = imageService;
        this.s3BucketService = s3BucketService;
    }

    @GetMapping
    public ResponseEntity<Page<ImageDTO>> getAllImages(Pageable pageable) {
        return ResponseEntity.ok(imageService.getAllImage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getOneImage(@PathVariable Long id) {
        return ResponseEntity.of(imageService.getOneImage(id));
    }

    @GetMapping("/{id}/download")
    public HttpEntity<byte[]> downloadImage(@PathVariable Long id) {
        return s3BucketService.downloadObject(id);
    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        return ResponseEntity.ok(imageService.createImage(imageDTO));
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Void> uploadImage(@PathVariable Long id,
                                            @RequestParam MultipartFile file) throws IOException {
        s3BucketService.uploadObject(id, file);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
