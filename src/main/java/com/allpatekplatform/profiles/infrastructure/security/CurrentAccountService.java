package com.allpatekplatform.profiles.infrastructure.security;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.profiles.domain.repositories.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentAccountService {

    private final ProfileRepository profileRepository;

    public CurrentAccountService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<String> username() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        String name = authentication.getName();
        if (name == null || name.isBlank() || "anonymousUser".equals(name)) {
            return Optional.empty();
        }
        return Optional.of(name);
    }

    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(authority -> role.equals(authority.getAuthority()));
    }

    public boolean isFarmer() {
        return hasRole("ROLE_FARMER");
    }

    public boolean isMerchant() {
        return hasRole("ROLE_MERCHANT");
    }

    public Optional<Profile> profile() {
        return username().flatMap(username -> {
            try {
                return profileRepository.findByEmailAddress(new EmailAddress(username));
            } catch (RuntimeException exception) {
                return Optional.empty();
            }
        });
    }

    public Optional<Long> profileId() {
        return profile().map(profile -> profile.getId().value());
    }

    public Optional<String> fullName() {
        return profile()
                .map(profile -> profile.getFullName().value())
                .filter(name -> name != null && !name.isBlank());
    }

    public Result<Profile, ApplicationError> requireProfile() {
        return profile()
                .<Result<Profile, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("PROFILE", username().orElse("anonymous"))));
    }
}
