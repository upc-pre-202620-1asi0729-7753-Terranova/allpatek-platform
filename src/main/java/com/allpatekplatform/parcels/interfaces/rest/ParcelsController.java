package com.allpatekplatform.parcels.interfaces.rest;

import com.allpatekplatform.parcels.application.commandservices.ParcelCommandService;
import com.allpatekplatform.parcels.application.queryservices.ParcelQueryService;
import com.allpatekplatform.parcels.domain.model.commands.DeleteParcelCommand;
import com.allpatekplatform.parcels.domain.model.queries.GetAllParcelsQuery;
import com.allpatekplatform.parcels.interfaces.rest.resources.ParcelResource;
import com.allpatekplatform.parcels.interfaces.rest.transform.ParcelResourceAssembler;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value = "/api/v1/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "parcels")
public class ParcelsController {

    private final ParcelCommandService parcelCommandService;
    private final ParcelQueryService parcelQueryService;

    public ParcelsController(ParcelCommandService parcelCommandService, ParcelQueryService parcelQueryService) {
        this.parcelCommandService = parcelCommandService;
        this.parcelQueryService = parcelQueryService;
    }

    @GetMapping
    public List<ParcelResource> getParcels(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return parcelQueryService.handle(new GetAllParcelsQuery()).stream()
                .map(ParcelResourceAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> createParcel(
            @Valid @RequestBody ParcelResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                parcelCommandService.handle(ParcelResourceAssembler.toCreateCommand(resource)),
                ParcelResourceAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParcel(
            @PathVariable String id,
            @Valid @RequestBody ParcelResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                parcelCommandService.handle(ParcelResourceAssembler.toUpdateCommand(id, resource)),
                ParcelResourceAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParcel(
            @PathVariable String id,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var result = parcelCommandService.handle(new DeleteParcelCommand(id));
        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        };
    }
}
