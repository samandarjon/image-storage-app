package io.storage.imagestorageapp.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String name;
    private String customName;
    private String extension;
    private Long size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
