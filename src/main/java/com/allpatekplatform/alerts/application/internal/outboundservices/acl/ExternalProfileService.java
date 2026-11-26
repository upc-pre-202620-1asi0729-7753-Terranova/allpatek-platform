package com.allpatekplatform.alerts.application.internal.outboundservices.acl;

import com.allpatekplatform.profiles.interfaces.acl.ProfilesContextFacade;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("alertsExternalProfileService")
public class ExternalProfileService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Optional<ProfileId> fetchProfileByEmail(String email) {
        if (email == null || email.isBlank()) return Optional.empty();
        var profileId = profilesContextFacade.fetchProfileIdByEmail(email);
        return profileId == null || profileId <= 0
                ? Optional.empty() : Optional.of(new ProfileId(profileId));
    }
}
