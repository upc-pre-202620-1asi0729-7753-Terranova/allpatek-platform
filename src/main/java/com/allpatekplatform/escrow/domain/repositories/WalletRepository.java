package com.allpatekplatform.escrow.domain.repositories;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    boolean existsById(String id);

    List<Wallet> findAll();

    void deleteById(String id);
}
