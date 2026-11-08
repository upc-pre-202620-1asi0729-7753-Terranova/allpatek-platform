package com.allpatekplatform.tracking.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EvidenceFilePersistenceEmbeddable {

    @Column(name = "file_name", length = 240)
    private String fileName;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    public EvidenceFilePersistenceEmbeddable() {
    }

    public EvidenceFilePersistenceEmbeddable(String fileName, String image) {
        this.fileName = fileName;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
