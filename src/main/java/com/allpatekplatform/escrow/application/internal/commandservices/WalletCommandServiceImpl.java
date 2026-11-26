package com.allpatekplatform.escrow.application.internal.commandservices;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.escrow.application.commandservices.WalletCommandService;
import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.commands.CreateWalletByProfileIdCommand;
import com.allpatekplatform.escrow.domain.model.commands.CreditWalletCommand;
import com.allpatekplatform.escrow.domain.model.commands.DeleteWalletCommand;
import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;
import com.allpatekplatform.escrow.domain.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletCommandServiceImpl implements WalletCommandService {

    private final WalletRepository walletRepository;

    public WalletCommandServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public Result<Wallet, ApplicationError> handle(SaveWalletCommand command) {
        return Result.success(walletRepository.save(new Wallet(command)));
    }

    @Override
    @Transactional
    public Result<Wallet, ApplicationError> handle(CreateWalletByProfileIdCommand command) {
        if (command.profileId() == null || command.profileId() <= 0L) {
            return Result.failure(ApplicationError.validationError("profileId", "Profile is required"));
        }
        var existing = walletRepository.findByProfileId(command.profileId());
        if (existing.isPresent()) {
            return Result.success(existing.get());
        }
        return Result.success(walletRepository.save(Wallet.provision(command.profileId(), command.holder())));
    }

    @Override
    @Transactional
    public Result<Wallet, ApplicationError> handle(CreditWalletCommand command) {
        var existing = walletRepository.findByProfileId(command.profileId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("WALLET", String.valueOf(command.profileId())));
        }
        if (command.cents() < 1) {
            return Result.failure(ApplicationError.validationError("cents", "Credit must be greater than zero"));
        }
        return Result.success(walletRepository.save(existing.get().credit(command.cents())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteWalletCommand command) {
        if (!walletRepository.existsById(command.id())) {
            return Result.failure(ApplicationError.notFound("WALLET", command.id()));
        }
        walletRepository.deleteById(command.id());
        return Result.success(null);
    }
}
