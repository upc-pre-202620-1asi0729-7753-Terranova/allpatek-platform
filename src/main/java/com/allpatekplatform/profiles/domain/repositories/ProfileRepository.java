package com.allpatekplatform.profiles.domain.repositories;

import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Profile save(Profile profile);

    Optional<Profile> findById(Long id);

    Optional<Profile> findByEmailAddress(EmailAddress emailAddress);

    List<Profile> findAll();
}
