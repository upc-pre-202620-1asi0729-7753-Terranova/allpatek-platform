package com.allpatekplatform.alerts.application.internal.outboundservices.acl;

import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public List<ParcelCoordinates> findLocations(Long ownerId) {
        return parcelsContextFacade.findLocations(ownerId).stream()
                .map(parcel -> {
                    try { return ParcelCoordinates.fromLocation(parcel.id(), parcel.name(), parcel.location()); }
                    catch (IllegalArgumentException exception) { return null; }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
