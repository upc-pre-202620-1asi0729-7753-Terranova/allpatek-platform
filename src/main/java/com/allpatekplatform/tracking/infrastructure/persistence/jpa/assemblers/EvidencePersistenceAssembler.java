package com.allpatekplatform.tracking.infrastructure.persistence.jpa.assemblers;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceFile;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceId;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceNotes;
import com.allpatekplatform.tracking.domain.model.valueobjects.MilestoneNumber;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.embeddables.EvidenceFilePersistenceEmbeddable;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;

/**
 * Static assembler between evidence domain and persistence representations.
 */
public final class EvidencePersistenceAssembler {

    private EvidencePersistenceAssembler() {
    }

    public static MilestoneEvidence toDomainFromPersistence(EvidencePersistenceEntity entity) {
        return new MilestoneEvidence(
                new EvidenceId(entity.getId()),
                new ParcelId(entity.getParcelId()),
                new MilestoneNumber(entity.getMilestone() == null ? 0 : entity.getMilestone()),
                entity.getStatus(),
                new EvidenceNotes(entity.getNotes()),
                toDomainFromPersistence(entity.getFile()));
    }

    public static EvidencePersistenceEntity toPersistenceFromDomain(MilestoneEvidence evidence) {
        EvidencePersistenceEntity entity = new EvidencePersistenceEntity();
        entity.setId(evidence.getId().value());
        entity.setParcelId(evidence.getParcelId().value());
        entity.setMilestone(evidence.getMilestone().value());
        entity.setStatus(evidence.getStatus());
        entity.setNotes(evidence.getNotes().value());
        entity.setFile(toPersistenceFromDomain(evidence.getFile()));
        return entity;
    }

    private static EvidenceFile toDomainFromPersistence(EvidenceFilePersistenceEmbeddable value) {
        return value == null ? new EvidenceFile("", "") : new EvidenceFile(value.getFileName(), value.getImage());
    }

    private static EvidenceFilePersistenceEmbeddable toPersistenceFromDomain(EvidenceFile value) {
        return value == null ? null : new EvidenceFilePersistenceEmbeddable(value.fileName(), value.image());
    }
}
