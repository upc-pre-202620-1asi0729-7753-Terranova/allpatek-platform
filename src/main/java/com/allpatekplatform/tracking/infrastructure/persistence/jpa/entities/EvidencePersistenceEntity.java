package com.allpatekplatform.tracking.infrastructure.persistence.jpa.entities;

import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceStatus;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.embeddables.EvidenceFilePersistenceEmbeddable;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.entities.StringIdentifiablePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evidence")
public class EvidencePersistenceEntity extends StringIdentifiablePersistenceEntity {

    @Column(name = "parcel_id", nullable = false, length = 160)
    private String parcelId;

    @Column(nullable = false)
    private Integer milestone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EvidenceStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Embedded
    private EvidenceFilePersistenceEmbeddable file;
}
