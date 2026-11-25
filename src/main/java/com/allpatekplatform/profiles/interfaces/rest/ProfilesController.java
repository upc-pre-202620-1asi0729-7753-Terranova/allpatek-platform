package com.allpatekplatform.profiles.interfaces.rest;

import com.allpatekplatform.profiles.application.commandservices.ProfileCommandService;
import com.allpatekplatform.profiles.application.queryservices.ProfileQueryService;
import com.allpatekplatform.profiles.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.allpatekplatform.profiles.infrastructure.security.CurrentAccountService;
import com.allpatekplatform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.allpatekplatform.profiles.interfaces.rest.resources.ProfileResource;
import com.allpatekplatform.profiles.interfaces.rest.resources.UpdateProfileResource;
import com.allpatekplatform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.allpatekplatform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.allpatekplatform.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Farmer and merchant profile endpoints")
public class ProfilesController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
    private final CurrentAccountService currentAccountService;

    public ProfilesController(
            ProfileCommandService profileCommandService,
            ProfileQueryService profileQueryService,
            CurrentAccountService currentAccountService
    ) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
        this.currentAccountService = currentAccountService;
    }

    @PostMapping
    @Operation(summary = "Create a profile", description = "Creates the signed-in person's civil and company profile.")
    public ResponseEntity<?> createProfile(
            @Valid @RequestBody CreateProfileResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        String email = currentAccountService.username().orElse(resource.email());
        return ResponseEntityAssembler.toResponseEntityFromResult(
                profileCommandService.handle(
                        CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource, email)),
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current profile")
    public ResponseEntity<?> getCurrentProfile(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return currentAccountService.profile()
                .<ResponseEntity<?>>map(profile -> ResponseEntity.ok(
                        ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("PROFILE", currentAccountService.username().orElse("me"))));
    }

    @GetMapping
    public List<ProfileResource> getProfiles(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return profileQueryService.handle(new GetAllProfilesQuery()).stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return profileQueryService.handle(new GetProfileByIdQuery(id))
                .<ResponseEntity<?>>map(profile -> ResponseEntity.ok(
                        ProfileResourceFromEntityAssembler.toResourceFromEntity(profile)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("PROFILE", String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        Long profileId = currentAccountService.profileId().orElse(id);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                profileCommandService.handle(
                        UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource)),
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
