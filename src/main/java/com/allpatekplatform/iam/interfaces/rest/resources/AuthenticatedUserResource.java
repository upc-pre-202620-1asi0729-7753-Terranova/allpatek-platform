package com.allpatekplatform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "AuthenticatedUserResponse", description = "Authenticated user information with JWT token")
public record AuthenticatedUserResource(
        @Schema(description = "User unique identifier", example = "1")
        Long id,
        @Schema(description = "Username, typically the account email", example = "gustavo.suyo@allpatek.demo")
        String username,
        @Schema(description = "JWT Bearer token")
        String token,
        @Schema(description = "Assigned roles", example = "[\"ROLE_FARMER\"]")
        List<String> roles,
        @Schema(description = "Linked profile identifier, if a profile exists")
        Long profileId
) {
}
