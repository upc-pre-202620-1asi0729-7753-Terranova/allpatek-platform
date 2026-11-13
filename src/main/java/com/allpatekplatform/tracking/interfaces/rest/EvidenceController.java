package com.allpatekplatform.tracking.interfaces.rest;

import com.allpatekplatform.tracking.application.commandservices.EvidenceCommandService;
import com.allpatekplatform.tracking.application.queryservices.EvidenceQueryService;
import com.allpatekplatform.tracking.domain.model.commands.DeleteEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.queries.GetAllEvidenceQuery;
import com.allpatekplatform.tracking.interfaces.rest.resources.EvidenceResource;
import com.allpatekplatform.tracking.interfaces.rest.transform.EvidenceResourceAssembler;
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
@RequestMapping(value = "/api/v1/evidence", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "tracking")
public class EvidenceController {

    private final EvidenceCommandService evidenceCommandService;
    private final EvidenceQueryService evidenceQueryService;

    public EvidenceController(
            EvidenceCommandService evidenceCommandService,
            EvidenceQueryService evidenceQueryService
    ) {
        this.evidenceCommandService = evidenceCommandService;
        this.evidenceQueryService = evidenceQueryService;
    }

    @GetMapping
    public List<EvidenceResource> getEvidence(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return evidenceQueryService.handle(new GetAllEvidenceQuery()).stream()
                .map(EvidenceResourceAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> createEvidence(
            @Valid @RequestBody EvidenceResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                evidenceCommandService.handle(EvidenceResourceAssembler.toCommand(resource.id(), resource)),
                EvidenceResourceAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvidence(
            @PathVariable String id,
            @Valid @RequestBody EvidenceResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                evidenceCommandService.handle(EvidenceResourceAssembler.toCommand(id, resource)),
                EvidenceResourceAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvidence(
            @PathVariable String id,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var result = evidenceCommandService.handle(new DeleteEvidenceCommand(id));
        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        };
    }
}
