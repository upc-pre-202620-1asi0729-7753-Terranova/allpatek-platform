package com.allpatekplatform.escrow.application.commandservices;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;

public interface WalletCommandService {

    Result<Wallet, ApplicationError> handle(SaveWalletCommand command);
}
