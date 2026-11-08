package com.allpatekplatform.parcels.application.commandservices;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.commands.CreateParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.DeleteParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.UpdateParcelCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface ParcelCommandService {

    Result<Parcel, ApplicationError> handle(CreateParcelCommand command);

    Result<Parcel, ApplicationError> handle(UpdateParcelCommand command);

    Result<Void, ApplicationError> handle(DeleteParcelCommand command);
}
