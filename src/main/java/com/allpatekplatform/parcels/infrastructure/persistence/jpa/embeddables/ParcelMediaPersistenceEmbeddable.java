package com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables;

import com.allpatekplatform.shared.infrastructure.persistence.jpa.converters.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

import java.util.List;

@Embeddable
public class ParcelMediaPersistenceEmbeddable {

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "LONGTEXT")
    private List<String> images;

    public ParcelMediaPersistenceEmbeddable() {
    }

    public ParcelMediaPersistenceEmbeddable(String imageUrl, List<String> images) {
        this.imageUrl = imageUrl;
        this.images = images;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
