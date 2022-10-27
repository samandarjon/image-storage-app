package io.storage.imagestorageapp.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByIdAndIsActiveTrue(Long id);

}
