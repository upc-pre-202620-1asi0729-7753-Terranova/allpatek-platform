package com.allpatekplatform.escrow.application.commandservices;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.commands.CreateWalletByProfileIdCommand;
import com.allpatekplatform.escrow.domain.model.commands.CreditWalletCommand;
import com.allpatekplatform.escrow.domain.model.commands.DeleteWalletCommand;
import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;

public interface WalletCommandService {

    Result<Wallet, ApplicationError> handle(SaveWalletCommand command);

    Result<Wallet, ApplicationError> handle(CreateWalletByProfileIdCommand command);

    Result<Wallet, ApplicationError> handle(CreditWalletCommand command);

    Result<Void, ApplicationError> handle(DeleteWalletCommand command);
}
