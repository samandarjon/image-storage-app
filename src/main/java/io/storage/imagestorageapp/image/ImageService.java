package io.storage.imagestorageapp.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
public interface ImageService {
    Page<ImageDTO> getAllImage(Pageable pageable);

    Optional<ImageDTO> getOneImage(Long id);

    ImageDTO createImage(ImageDTO imageDTO);

    void deleteImage(Long id);
}
