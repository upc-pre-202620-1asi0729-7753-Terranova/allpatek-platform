package com.allpatekplatform.iam.interfaces.rest;

import com.allpatekplatform.iam.application.commandservices.UserCommandService;
import com.allpatekplatform.iam.application.internal.outboundservices.acl.ExternalProfileService;
import com.allpatekplatform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.allpatekplatform.iam.interfaces.rest.resources.SignInResource;
import com.allpatekplatform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.allpatekplatform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that handles authentication and security token generation.
 */
@RestController
@RequestMapping(value = "/api/v1/tokens", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tokens", description = "Authentication and security token management endpoints")
public class TokensController {
    private final UserCommandService userCommandService;
    private final ExternalProfileService externalProfileService;

    public TokensController(UserCommandService userCommandService, ExternalProfileService externalProfileService) {
        this.userCommandService = userCommandService;
        this.externalProfileService = externalProfileService;
    }

    @PostMapping
    @Operation(
        summary = "Create authentication token (Sign-in)",
        description = "Authenticates a user with provided credentials and returns a JWT token for subsequent requests."
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Token generated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticatedUserResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found with provided username")
    })
    public ResponseEntity<?> generateToken(
            @Valid @RequestBody SignInResource signInResource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var result = userCommandService.handle(signInCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                auth -> AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
                        auth.getLeft(),
                        auth.getRight(),
                        profileIdFor(auth.getLeft().getUsername())),
                HttpStatus.OK
        );
    }

    private Long profileIdFor(String username) {
        return externalProfileService.fetchProfileIdByEmail(username).orElse(null);
    }
}
