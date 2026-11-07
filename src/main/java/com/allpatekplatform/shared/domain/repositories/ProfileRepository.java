package com.allpatekplatform.shared.domain.repositories;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    Profile save(Profile profile);

    Optional<Profile> findById(Long id);

    Optional<Profile> findByEmailAddress(EmailAddress emailAddress);

    List<Profile> findAll();
}
