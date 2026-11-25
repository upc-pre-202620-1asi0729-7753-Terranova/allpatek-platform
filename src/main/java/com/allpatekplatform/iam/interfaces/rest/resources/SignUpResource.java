package com.allpatekplatform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
    name = "SignUpRequest",
    description = "User sign-up request with credentials and roles",
    example = "{\"username\": \"gustavo.suyo@allpatek.demo\", \"password\": \"Allpatek2026!\", \"roles\": [\"ROLE_FARMER\"]}"
)
public record SignUpResource(
    @Schema(
        description = "Account email used as username",
        example = "gustavo.suyo@allpatek.demo",
        minLength = 3,
        maxLength = 160
    )
    String username,

    @Schema(
        description = "User password (minimum 8 characters)",
        example = "SecurePass123!",
        minLength = 8,
        maxLength = 255
    )
    String password,

    @Schema(
        description = "Roles to assign to the user",
        example = "[\"ROLE_FARMER\"]"
    )
    List<String> roles
) {
}
