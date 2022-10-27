package io.storage.imagestorageapp.image;

import io.storage.imagestorageapp.exceptions.S3ObjectNotFoundException;
import io.storage.imagestorageapp.s3.S3BucketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final S3BucketService s3BucketService;

    public ImageServiceImpl(ImageRepository imageRepository, S3BucketService s3BucketService) {
        this.imageRepository = imageRepository;
        this.s3BucketService = s3BucketService;
    }

    @Override
    public Page<ImageDTO> getAllImage(Pageable pageable) {
        return imageRepository.findAll(pageable)
                .map(Image::mapToDto);
    }

    @Override
    public Optional<ImageDTO> getOneImage(Long id) {
        return imageRepository.findByIdAndIsActiveTrue(id).map(Image::mapToDto);
    }

    @Override
    public ImageDTO createImage(ImageDTO imageDTO) {
        Image image = new Image();
//        image.setName(imageDTO.name());
//        image.setExtension(ImageUtil.extractExtension(imageDTO.name()));
        image.setSize(0L);
        image.setActive(false);
        image = imageRepository.save(image);
        return image.mapToDto();
    }

    @Override
    @Transactional
    public void deleteImage(Long id) {
        Image image = imageRepository.findByIdAndIsActiveTrue(id).orElseThrow(S3ObjectNotFoundException::new);
        s3BucketService.deleteObject(image.getName());
        imageRepository.delete(image);
    }

}
