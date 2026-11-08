package com.allpatekplatform.parcels.application.internal.queryservices;

import com.allpatekplatform.parcels.application.queryservices.ParcelQueryService;
import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.queries.GetAllParcelsQuery;
import com.allpatekplatform.parcels.domain.repositories.ParcelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParcelQueryServiceImpl implements ParcelQueryService {

    private final ParcelRepository parcelRepository;

    public ParcelQueryServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parcel> handle(GetAllParcelsQuery query) {
        return parcelRepository.findAll();
    }
}
