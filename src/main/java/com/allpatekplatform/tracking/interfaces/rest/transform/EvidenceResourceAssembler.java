package com.allpatekplatform.tracking.interfaces.rest.transform;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.interfaces.rest.resources.EvidenceResource;

public final class EvidenceResourceAssembler {

    private EvidenceResourceAssembler() {
    }

    public static EvidenceResource toResourceFromEntity(MilestoneEvidence evidence) {
        return new EvidenceResource(
                evidence.getId().value(),
                evidence.getParcelId().value(),
                evidence.getMilestone().value(),
                evidence.getStatus().name(),
                evidence.getNotes().value(),
                evidence.getFile().fileName(),
                evidence.getFile().image());
    }

    public static SaveEvidenceCommand toCommand(String id, EvidenceResource resource) {
        return new SaveEvidenceCommand(
                id != null ? id : resource.id(),
                resource.parcelId(),
                resource.milestone() == null ? 0 : resource.milestone(),
                resource.status(),
                resource.notes(),
                resource.fileName(),
                resource.image());
    }
}
