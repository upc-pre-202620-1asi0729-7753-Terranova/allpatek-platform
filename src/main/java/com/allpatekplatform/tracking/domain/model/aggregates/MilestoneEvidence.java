package com.allpatekplatform.tracking.domain.model.aggregates;

import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceFile;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceId;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceNotes;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceStatus;
import com.allpatekplatform.tracking.domain.model.valueobjects.MilestoneNumber;
import com.allpatekplatform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import lombok.Getter;

import java.util.Objects;

/**
 * Milestone evidence aggregate root. Persistence lives in {@code EvidencePersistenceEntity}.
 */
@Getter
public class MilestoneEvidence extends AbstractDomainAggregateRoot<MilestoneEvidence> {

    private final EvidenceId id;
    private final ParcelId parcelId;
    private final MilestoneNumber milestone;
    private final EvidenceStatus status;
    private final EvidenceNotes notes;
    private final EvidenceFile file;

    public MilestoneEvidence(
            EvidenceId id,
            ParcelId parcelId,
            MilestoneNumber milestone,
            EvidenceStatus status,
            EvidenceNotes notes,
            EvidenceFile file
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.parcelId = Objects.requireNonNull(parcelId, "parcelId must not be null");
        this.milestone = Objects.requireNonNull(milestone, "milestone must not be null");
        this.status = status == null ? EvidenceStatus.review : status;
        this.notes = notes == null ? new EvidenceNotes("") : notes;
        this.file = file == null ? new EvidenceFile("", "") : file;
    }

    public MilestoneEvidence(SaveEvidenceCommand command) {
        this(
                new EvidenceId(command.id()),
                new ParcelId(command.parcelId()),
                new MilestoneNumber(command.milestone()),
                EvidenceStatus.from(command.status()),
                new EvidenceNotes(command.notes()),
                new EvidenceFile(command.fileName(), command.image()));
    }
}
