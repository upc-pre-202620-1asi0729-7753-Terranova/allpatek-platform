package com.allpatekplatform.iam.interfaces.rest.transform;

import com.allpatekplatform.iam.domain.model.aggregates.User;
import com.allpatekplatform.iam.domain.model.entities.Role;
import com.allpatekplatform.iam.interfaces.rest.resources.AuthenticatedUserResource;

import java.util.List;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token, Long profileId) {
        List<String> roles = user.getRoles() == null
                ? List.of()
                : user.getRoles().stream().map(Role::getStringName).toList();
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token, roles, profileId);
    }
}
