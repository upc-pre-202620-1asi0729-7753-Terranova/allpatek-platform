package com.allpatekplatform.escrow.application.internal.queryservices;

import com.allpatekplatform.escrow.application.queryservices.WalletQueryService;
import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.queries.GetAllWalletsQuery;
import com.allpatekplatform.escrow.domain.repositories.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WalletQueryServiceImpl implements WalletQueryService {

    private final WalletRepository walletRepository;

    public WalletQueryServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wallet> handle(GetAllWalletsQuery query) {
        return walletRepository.findAll();
    }
}
