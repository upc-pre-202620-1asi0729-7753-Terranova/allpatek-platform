package com.allpatekplatform.parcels.interfaces.acl;

import java.util.List;

public interface ParcelsContextFacade {

    List<ParcelLocation> findLocations(Long ownerId);
}
