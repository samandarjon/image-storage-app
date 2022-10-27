package io.storage.imagestorageapp.image;

import java.time.LocalDateTime;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */
public class ImageDTO {
    private Long id;
    private String name;
    private String customName;
    private String extension;
    private Long size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAT;

    public ImageDTO(Long id, String name, String customName, String extension, Long size, LocalDateTime createdAt, LocalDateTime updatedAT) {
        this.id = id;
        this.name = name;
        this.customName = customName;
        this.extension = extension;
        this.size = size;
        this.createdAt = createdAt;
        this.updatedAT = updatedAT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }
}
