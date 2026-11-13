package com.allpatekplatform.agreements.interfaces.rest;

import com.allpatekplatform.agreements.application.commandservices.AgreementCommandService;
import com.allpatekplatform.agreements.application.queryservices.AgreementQueryService;
import com.allpatekplatform.agreements.domain.model.commands.DeleteAgreementCommand;
import com.allpatekplatform.agreements.domain.model.queries.GetAllAgreementsQuery;
import com.allpatekplatform.agreements.interfaces.rest.resources.AgreementResource;
import com.allpatekplatform.agreements.interfaces.rest.transform.AgreementResourceAssembler;
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
@RequestMapping(value = "/api/v1/agreements", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "agreements")
public class AgreementsController {

    private final AgreementCommandService agreementCommandService;
    private final AgreementQueryService agreementQueryService;

    public AgreementsController(
            AgreementCommandService agreementCommandService,
            AgreementQueryService agreementQueryService
    ) {
        this.agreementCommandService = agreementCommandService;
        this.agreementQueryService = agreementQueryService;
    }

    @GetMapping
    public List<AgreementResource> getAgreements(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return agreementQueryService.handle(new GetAllAgreementsQuery()).stream()
                .map(AgreementResourceAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> createAgreement(
            @Valid @RequestBody AgreementResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                agreementCommandService.handle(AgreementResourceAssembler.toCommand(resource.id(), resource)),
                AgreementResourceAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAgreement(
            @PathVariable String id,
            @Valid @RequestBody AgreementResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                agreementCommandService.handle(AgreementResourceAssembler.toCommand(id, resource)),
                AgreementResourceAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAgreement(
            @PathVariable String id,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var result = agreementCommandService.handle(new DeleteAgreementCommand(id));
        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        };
    }
}
