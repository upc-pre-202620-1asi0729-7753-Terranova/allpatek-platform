package com.allpatekplatform.tracking.domain.repositories;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;

import java.util.List;

public interface EvidenceRepository {

    MilestoneEvidence save(MilestoneEvidence evidence);

    boolean existsById(String id);

    List<MilestoneEvidence> findAll();

    void deleteById(String id);
}
