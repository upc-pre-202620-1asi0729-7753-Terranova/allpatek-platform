package com.allpatekplatform.escrow.application.internal.commandservices;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.escrow.application.commandservices.WalletCommandService;
import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
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
}
