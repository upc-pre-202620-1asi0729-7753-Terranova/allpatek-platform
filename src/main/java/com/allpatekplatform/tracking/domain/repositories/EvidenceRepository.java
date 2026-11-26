package com.allpatekplatform.tracking.domain.repositories;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;

import java.util.List;
import java.util.Optional;

public interface EvidenceRepository {

    MilestoneEvidence save(MilestoneEvidence evidence);

    Optional<MilestoneEvidence> findById(String id);

    boolean existsById(String id);

    List<MilestoneEvidence> findAll();

    void deleteById(String id);
}
