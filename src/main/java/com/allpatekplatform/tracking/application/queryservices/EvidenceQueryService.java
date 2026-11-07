package com.allpatekplatform.tracking.application.queryservices;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.queries.GetAllEvidenceQuery;

import java.util.List;

public interface EvidenceQueryService {

    List<MilestoneEvidence> handle(GetAllEvidenceQuery query);
}
