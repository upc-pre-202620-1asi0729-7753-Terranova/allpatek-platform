package com.allpatekplatform.shared.application.queryservices;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.shared.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {

    List<Profile> handle(GetAllProfilesQuery query);

    Optional<Profile> handle(GetProfileByIdQuery query);
}
