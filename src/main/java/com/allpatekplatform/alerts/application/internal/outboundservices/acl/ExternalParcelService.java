package com.allpatekplatform.alerts.application.internal.outboundservices.acl;

import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

/**
 * ACL service used by the Alerts bounded context to read parcel locations
 * without depending on the parcels domain model.
 */
@Service
public class ExternalParcelService {

    private final ParcelsContextFacade parcelsContextFacade;

    public ExternalParcelService(ParcelsContextFacade parcelsContextFacade) {
        this.parcelsContextFacade = parcelsContextFacade;
    }

    public Result<List<ParcelCoordinates>, ApplicationError> findLocations(Long ownerId) {
        var locations = parcelsContextFacade.findLocations(ownerId);
        var coordinates = new java.util.ArrayList<ParcelCoordinates>();
        for (var parcel : locations) {
            try {
                coordinates.add(ParcelCoordinates.fromLocation(parcel.id(), parcel.name(), parcel.location()));
            } catch (IllegalArgumentException exception) {
                return Result.failure(ApplicationError.validationError("parcel.location",
                        "Invalid coordinates for parcel " + parcel.id()));
            }
        }
        return Result.success(List.copyOf(coordinates));
    }
}
