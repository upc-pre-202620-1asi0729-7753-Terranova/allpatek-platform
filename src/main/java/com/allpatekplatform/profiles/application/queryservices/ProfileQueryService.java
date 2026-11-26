package com.allpatekplatform.profiles.application.queryservices;

import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {

    List<Profile> handle(GetAllProfilesQuery query);

    Optional<Profile> handle(GetProfileByIdQuery query);

    Optional<Profile> handle(GetProfileByEmailQuery query);
}
