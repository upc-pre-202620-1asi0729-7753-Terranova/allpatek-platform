package com.allpatekplatform.iam.application.internal.outboundservices.acl;

import com.allpatekplatform.profiles.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL service used by the IAM bounded context to interact with Profiles capabilities.
 */
@Service
public class ExternalProfileService {

    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Optional<Long> fetchProfileIdByEmail(String email) {
        Long profileId = profilesContextFacade.fetchProfileIdByEmail(email);
        return profileId == null || profileId == 0L ? Optional.empty() : Optional.of(profileId);
    }

    public Optional<Long> createProfile(
            String fullName,
            String phone,
            String document,
            String address,
            String email,
            String company,
            String ruc,
            String activity
    ) {
        Long profileId = profilesContextFacade.createProfile(
                fullName, phone, document, address, email, company, ruc, activity);
        return profileId == null || profileId == 0L ? Optional.empty() : Optional.of(profileId);
    }
}
