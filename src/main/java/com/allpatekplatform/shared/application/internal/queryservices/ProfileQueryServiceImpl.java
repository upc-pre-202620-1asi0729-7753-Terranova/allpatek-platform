package com.allpatekplatform.shared.application.internal.queryservices;

import com.allpatekplatform.shared.application.queryservices.ProfileQueryService;
import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.shared.domain.model.queries.GetProfileByIdQuery;
import com.allpatekplatform.shared.domain.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.id());
    }
}
