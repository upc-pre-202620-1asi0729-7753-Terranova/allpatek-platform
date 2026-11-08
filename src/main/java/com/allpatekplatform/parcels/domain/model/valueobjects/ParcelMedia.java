package com.allpatekplatform.parcels.domain.model.valueobjects;

import java.util.List;

public record ParcelMedia(String imageUrl, List<String> images) {

    public ParcelMedia {
        imageUrl = imageUrl == null ? "" : imageUrl;
        images = images != null && !images.isEmpty()
                ? List.copyOf(images)
                : (imageUrl.isBlank() ? List.of() : List.of(imageUrl));
    }
}
