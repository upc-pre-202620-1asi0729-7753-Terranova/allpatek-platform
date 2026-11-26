package com.allpatekplatform.profiles.application.acl;

import com.allpatekplatform.profiles.application.commandservices.ProfileCommandService;
import com.allpatekplatform.profiles.application.queryservices.ProfileQueryService;
import com.allpatekplatform.profiles.domain.model.commands.CreateProfileCommand;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByEmailQuery;
import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Profiles ACL facade.
 */
@Service
public class ProfilesContextFacadeImpl implements ProfilesContextFacade {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesContextFacadeImpl(
            ProfileCommandService profileCommandService,
            ProfileQueryService profileQueryService
    ) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @Override
    public Long createProfile(
            String fullName,
            String phone,
            String document,
            String address,
            String email,
            String company,
            String ruc,
            String activity
    ) {
        var result = profileCommandService.handle(new CreateProfileCommand(
                fullName, phone, document, address, email, company, ruc, activity));
        return result.toOptional()
                .map(profile -> profile.getId().value())
                .orElse(0L);
    }

    @Override
    public Long fetchProfileIdByEmail(String email) {
        try {
            var profile = profileQueryService.handle(new GetProfileByEmailQuery(new EmailAddress(email)));
            return profile.map(found -> found.getId().value()).orElse(0L);
        } catch (RuntimeException exception) {
            return 0L;
        }
    }
}
