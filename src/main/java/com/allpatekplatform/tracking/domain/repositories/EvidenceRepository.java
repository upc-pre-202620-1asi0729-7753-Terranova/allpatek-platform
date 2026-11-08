package com.allpatekplatform.tracking.domain.repositories;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;

import java.util.List;

public interface EvidenceRepository {

    MilestoneEvidence save(MilestoneEvidence evidence);

    List<MilestoneEvidence> findAll();
}
