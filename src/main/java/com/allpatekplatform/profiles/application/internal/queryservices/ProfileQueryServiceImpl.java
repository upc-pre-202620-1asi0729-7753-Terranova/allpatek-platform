package com.allpatekplatform.profiles.application.internal.queryservices;

import com.allpatekplatform.profiles.application.queryservices.ProfileQueryService;
import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.allpatekplatform.profiles.domain.repositories.ProfileRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> handle(GetProfileByEmailQuery query) {
        return profileRepository.findByEmailAddress(query.emailAddress());
    }
}
