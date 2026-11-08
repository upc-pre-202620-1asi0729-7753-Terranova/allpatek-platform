package com.allpatekplatform.shared.application.internal.commandservices;

import com.allpatekplatform.shared.application.commandservices.ProfileCommandService;
import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.shared.domain.repositories.ProfileRepository;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public Result<Profile, ApplicationError> handle(UpdateProfileCommand command) {
        var existing = profileRepository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("PROFILE", String.valueOf(command.id())));
        }
        EmailAddress email = new EmailAddress(command.email());
        var taken = profileRepository.findByEmailAddress(email)
                .filter(profile -> !Objects.equals(profile.getId().value(), command.id()));
        if (taken.isPresent()) {
            return Result.failure(ApplicationError.conflict("PROFILE", "A profile already uses that email"));
        }
        Profile profile = existing.get();
        profile.update(command);
        return Result.success(profileRepository.save(profile));
    }
}
