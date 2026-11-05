package com.allpatekplatform.tracking.domain.model.valueobjects;

public record EvidenceFile(String fileName, String image) {

    public EvidenceFile {
        fileName = fileName == null ? "" : fileName.trim();
        image = image == null ? "" : image;
    }
}
