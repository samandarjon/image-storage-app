package io.storage.imagestorageapp.image;

import java.time.LocalDateTime;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
public record ImageDTO(Long id,
                       String name,
                       String customName,
                       String extension,
                       Long size,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
}
