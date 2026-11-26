package com.allpatekplatform.parcels.application.acl;

import com.allpatekplatform.parcels.domain.repositories.ParcelRepository;
import com.allpatekplatform.parcels.interfaces.acl.ParcelLocation;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application-layer implementation of the Parcels ACL facade.
 */
@Service
public class ParcelsContextFacadeImpl implements ParcelsContextFacade {

    private final ParcelRepository parcelRepository;

    public ParcelsContextFacadeImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public List<ParcelLocation> findLocations(Long ownerId) {
        if (ownerId == null || ownerId <= 0) throw new IllegalArgumentException("A positive owner id is required.");
        return parcelRepository.findByOwnerId(ownerId).stream()
                .map(parcel -> new ParcelLocation(
                        parcel.getId().value(),
                        parcel.getName().value(),
                        parcel.getLocation().value()))
                .toList();
    }
}
