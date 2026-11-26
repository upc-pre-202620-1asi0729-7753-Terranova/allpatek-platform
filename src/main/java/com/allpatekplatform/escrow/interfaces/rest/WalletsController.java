package com.allpatekplatform.escrow.interfaces.rest;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.profiles.infrastructure.security.CurrentAccountService;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.allpatekplatform.escrow.application.commandservices.WalletCommandService;
import com.allpatekplatform.escrow.application.queryservices.WalletQueryService;
import com.allpatekplatform.escrow.domain.model.commands.DeleteWalletCommand;
import com.allpatekplatform.escrow.domain.model.queries.GetAllWalletsQuery;
import com.allpatekplatform.escrow.interfaces.rest.resources.WalletResource;
import com.allpatekplatform.escrow.interfaces.rest.transform.WalletResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = "/api/v1/wallets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "escrow")
public class WalletsController {

    private final WalletCommandService walletCommandService;
    private final WalletQueryService walletQueryService;
    private final CurrentAccountService currentAccountService;

    public WalletsController(
            WalletCommandService walletCommandService,
            WalletQueryService walletQueryService,
            CurrentAccountService currentAccountService
    ) {
        this.walletCommandService = walletCommandService;
        this.walletQueryService = walletQueryService;
        this.currentAccountService = currentAccountService;
    }

    @GetMapping
    public List<WalletResource> getWallets(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return walletQueryService.handle(new GetAllWalletsQuery()).stream()
                .map(WalletResourceAssembler::toResourceFromEntity)
                .toList();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MERCHANT')")
    public ResponseEntity<?> createWallet(
            @Valid @RequestBody WalletResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                walletCommandService.handle(WalletResourceAssembler.toCommand(resource.id(), withOwner(resource))),
                WalletResourceAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MERCHANT')")
    public ResponseEntity<?> updateWallet(
            @PathVariable String id,
            @Valid @RequestBody WalletResource resource,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                walletCommandService.handle(WalletResourceAssembler.toCommand(id, withOwner(resource))),
                WalletResourceAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWallet(
            @PathVariable String id,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var result = walletCommandService.handle(new DeleteWalletCommand(id));
        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(failure.error());
        };
    }

    private WalletResource withOwner(WalletResource resource) {
        Long profileId = currentAccountService.profileId().orElse(resource.profileId());
        return new WalletResource(
                resource.id(),
                profileId,
                resource.holder(),
                resource.number(),
                resource.cvv(),
                resource.last4(),
                resource.expiry(),
                resource.brand(),
                resource.openingCents());
    }
}
