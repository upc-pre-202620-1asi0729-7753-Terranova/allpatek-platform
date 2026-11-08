package com.allpatekplatform.parcels.domain.repositories;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;

import java.util.List;
import java.util.Optional;

public interface ParcelRepository {

    Parcel save(Parcel parcel);

    Optional<Parcel> findById(String id);

    boolean existsById(String id);

    List<Parcel> findAll();

    void deleteById(String id);
}
