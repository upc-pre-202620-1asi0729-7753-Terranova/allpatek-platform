package com.allpatekplatform.escrow.application.queryservices;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.queries.GetAllWalletsQuery;

import java.util.List;

public interface WalletQueryService {

    List<Wallet> handle(GetAllWalletsQuery query);
}
