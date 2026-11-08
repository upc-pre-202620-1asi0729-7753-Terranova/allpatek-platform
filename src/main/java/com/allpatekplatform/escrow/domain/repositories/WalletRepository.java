package com.allpatekplatform.escrow.domain.repositories;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    List<Wallet> findAll();
}
