package com.allpatekplatform.parcels.application.queryservices;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.queries.GetAllParcelsQuery;

import java.util.List;

public interface ParcelQueryService {

    List<Parcel> handle(GetAllParcelsQuery query);
}
