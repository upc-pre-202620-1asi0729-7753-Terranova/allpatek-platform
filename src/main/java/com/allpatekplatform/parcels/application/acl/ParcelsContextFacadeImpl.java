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
        return parcelRepository.findAll().stream()
                .filter(parcel -> ownerId == null || ownerId.equals(parcel.getOwnerId().value()))
                .map(parcel -> new ParcelLocation(
                        parcel.getId().value(),
                        parcel.getName().value(),
                        parcel.getLocation().value()))
                .toList();
    }
}
