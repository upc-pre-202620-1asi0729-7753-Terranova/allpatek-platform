package com.allpatekplatform.parcels.application.internal.commandservices;

import com.allpatekplatform.parcels.application.commandservices.ParcelCommandService;
import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.commands.CreateParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.DeleteParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.MarkParcelInProductionCommand;
import com.allpatekplatform.parcels.domain.model.commands.UpdateParcelCommand;
import com.allpatekplatform.parcels.domain.repositories.ParcelRepository;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcelCommandServiceImpl implements ParcelCommandService {

    private final ParcelRepository parcelRepository;

    public ParcelCommandServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    @Transactional
    public Result<Parcel, ApplicationError> handle(CreateParcelCommand command) {
        if (command.id() == null || command.id().isBlank()) {
            return Result.failure(ApplicationError.validationError("id", "Parcel id is required"));
        }
        if (parcelRepository.existsById(command.id())) {
            return Result.failure(ApplicationError.conflict("PARCEL", "A parcel with that id already exists"));
        }
        return Result.success(parcelRepository.save(new Parcel(command)));
    }

    @Override
    @Transactional
    public Result<Parcel, ApplicationError> handle(UpdateParcelCommand command) {
        return parcelRepository.findById(command.id())
                .map(parcel -> {
                    parcel.update(command);
                    return Result.<Parcel, ApplicationError>success(parcelRepository.save(parcel));
                })
                .orElseGet(() -> Result.failure(ApplicationError.notFound("PARCEL", command.id())));
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteParcelCommand command) {
        if (!parcelRepository.existsById(command.id())) {
            return Result.failure(ApplicationError.notFound("PARCEL", command.id()));
        }
        parcelRepository.deleteById(command.id());
        return Result.success(null);
    }

    @Override
    @Transactional
    public Result<Parcel, ApplicationError> handle(MarkParcelInProductionCommand command) {
        return parcelRepository.findById(command.parcelId())
                .map(parcel -> {
                    parcel.markInProduction();
                    return Result.<Parcel, ApplicationError>success(parcelRepository.save(parcel));
                })
                .orElseGet(() -> Result.failure(ApplicationError.notFound("PARCEL", command.parcelId())));
    }
}
