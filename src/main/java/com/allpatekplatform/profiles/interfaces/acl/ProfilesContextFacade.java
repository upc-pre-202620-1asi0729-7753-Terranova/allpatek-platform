package com.allpatekplatform.profiles.interfaces.acl;

/**
 * ACL facade that exposes Profiles bounded context capabilities to other contexts.
 */
public interface ProfilesContextFacade {

    Long createProfile(
            String fullName,
            String phone,
            String document,
            String address,
            String email,
            String company,
            String ruc,
            String activity
    );

    Long fetchProfileIdByEmail(String email);
}
