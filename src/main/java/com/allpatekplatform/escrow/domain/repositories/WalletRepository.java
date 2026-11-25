package com.allpatekplatform.escrow.domain.repositories;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {

    Wallet save(Wallet wallet);

    boolean existsById(String id);

    Optional<Wallet> findByProfileId(Long profileId);

    List<Wallet> findAll();

    void deleteById(String id);
}
