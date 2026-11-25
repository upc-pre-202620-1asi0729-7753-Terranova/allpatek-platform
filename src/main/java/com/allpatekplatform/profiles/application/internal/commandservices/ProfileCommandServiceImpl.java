package com.allpatekplatform.profiles.application.internal.commandservices;

import com.allpatekplatform.profiles.application.commandservices.ProfileCommandService;
import com.allpatekplatform.profiles.domain.model.ProfileIdentityRules;
import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.commands.CreateProfileCommand;
import com.allpatekplatform.profiles.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.profiles.domain.model.valueobjects.CompanyDetails;
import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.profiles.domain.model.valueobjects.IdentityDocument;
import com.allpatekplatform.profiles.domain.model.valueobjects.PersonName;
import com.allpatekplatform.profiles.domain.model.valueobjects.PhoneNumber;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import com.allpatekplatform.profiles.domain.model.valueobjects.StreetAddress;
import com.allpatekplatform.profiles.domain.repositories.ProfileRepository;
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
    public Result<Profile, ApplicationError> handle(CreateProfileCommand command) {
        var identityIssue = ProfileIdentityRules.issueFor(
                command.fullName(),
                command.phone(),
                command.document(),
                command.address(),
                command.company(),
                command.ruc(),
                command.activity());
        if (identityIssue.isPresent()) {
            return Result.failure(ApplicationError.validationError(identityIssue.get(), "Invalid identity field"));
        }
        EmailAddress email = new EmailAddress(command.email());
        if (profileRepository.findByEmailAddress(email).isPresent()) {
            return Result.failure(ApplicationError.conflict("PROFILE", "A profile already uses that email"));
        }
        Profile profile = new Profile(
                new ProfileId(null),
                new PersonName(command.fullName()),
                new PhoneNumber(command.phone()),
                new IdentityDocument(command.document()),
                new StreetAddress(command.address()),
                email,
                new CompanyDetails(command.company(), command.ruc(), command.activity()));
        return Result.success(profileRepository.save(profile));
    }

    @Override
    @Transactional
    public Result<Profile, ApplicationError> handle(UpdateProfileCommand command) {
        var existing = profileRepository.findById(command.id());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("PROFILE", String.valueOf(command.id())));
        }
        var identityIssue = ProfileIdentityRules.issueFor(
                command.fullName(),
                command.phone(),
                command.document(),
                command.address(),
                command.company(),
                command.ruc(),
                command.activity());
        if (identityIssue.isPresent()) {
            return Result.failure(ApplicationError.validationError(identityIssue.get(), "Invalid identity field"));
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
