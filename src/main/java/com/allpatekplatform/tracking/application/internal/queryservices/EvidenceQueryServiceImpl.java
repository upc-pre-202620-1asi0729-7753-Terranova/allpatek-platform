package com.allpatekplatform.tracking.application.internal.queryservices;

import com.allpatekplatform.tracking.application.queryservices.EvidenceQueryService;
import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.queries.GetAllEvidenceQuery;
import com.allpatekplatform.tracking.domain.repositories.EvidenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvidenceQueryServiceImpl implements EvidenceQueryService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceQueryServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MilestoneEvidence> handle(GetAllEvidenceQuery query) {
        return evidenceRepository.findAll();
    }
}
