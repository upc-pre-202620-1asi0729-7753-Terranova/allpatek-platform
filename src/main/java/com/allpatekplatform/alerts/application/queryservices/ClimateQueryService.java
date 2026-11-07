package com.allpatekplatform.alerts.application.queryservices;

import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

import java.util.List;

public interface ClimateQueryService {

    Result<List<ClimateReading>, ApplicationError> handle(GetClimateAlertsQuery query);
}
