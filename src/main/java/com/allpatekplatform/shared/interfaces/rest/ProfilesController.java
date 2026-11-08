package com.allpatekplatform.shared.interfaces.rest;

import com.allpatekplatform.shared.application.commandservices.ProfileCommandService;
import com.allpatekplatform.shared.application.queryservices.ProfileQueryService;
import com.allpatekplatform.shared.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.shared.domain.model.queries.GetAllProfilesQuery;
import com.allpatekplatform.shared.domain.model.queries.GetProfileByIdQuery;
import com.allpatekplatform.shared.interfaces.rest.resources.ProfileResource;
import com.allpatekplatform.shared.interfaces.rest.resources.UpdateProfileResource;
import com.allpatekplatform.shared.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Hidden
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfilesController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(
            ProfileCommandService profileCommandService,
            ProfileQueryService profileQueryService
    ) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
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
        return ResponseEntityAssembler.toResponseEntityFromResult(
                profileCommandService.handle(new UpdateProfileCommand(
                        id,
                        resource.fullName(),
                        resource.phone(),
                        resource.document(),
                        resource.address(),
                        resource.email(),
                        resource.company(),
                        resource.ruc(),
                        resource.activity())),
                ProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
