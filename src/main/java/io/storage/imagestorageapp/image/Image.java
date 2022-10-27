package io.storage.imagestorageapp.image;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Author: Samandar_Akbarov
 * Date: 10/26/2022
 */

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String customName;
    private String extension;
    private Long size;
    private Boolean isActive = Boolean.FALSE;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomName() {
        return customName;
    }


    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public ImageDTO mapToDto() {
        return new ImageDTO(this.getId(),
                this.getName(),
                this.getCustomName(),
                this.getExtension(),
                this.getSize(),
                this.getCreatedAt(),
                this.getUpdatedAt());
    }

}
