package com.allpatekplatform.parcels.infrastructure.persistence.jpa.assemblers;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.valueobjects.CampaignCost;
import com.allpatekplatform.parcels.domain.model.valueobjects.CampaignSchedule;
import com.allpatekplatform.parcels.domain.model.valueobjects.Crops;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelArea;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelLocation;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelMedia;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelName;
import com.allpatekplatform.parcels.domain.model.valueobjects.SoilType;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables.CampaignSchedulePersistenceEmbeddable;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables.ParcelMediaPersistenceEmbeddable;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.entities.ParcelPersistenceEntity;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;

/**
 * Static assembler between parcel domain and persistence representations.
 */
public final class ParcelPersistenceAssembler {

    private ParcelPersistenceAssembler() {
    }

    public static Parcel toDomainFromPersistence(ParcelPersistenceEntity entity) {
        return new Parcel(
                new ParcelId(entity.getId()),
                new ProfileId(entity.getOwnerId()),
                new ParcelName(entity.getName()),
                entity.getStatus(),
                new ParcelArea(entity.getArea()),
                new ParcelLocation(entity.getLocation()),
                new SoilType(entity.getSoilType()),
                new CampaignCost(entity.getCampaignCost()),
                toDomainFromPersistence(entity.getSchedule()),
                Crops.of(entity.getCrops()),
                toDomainFromPersistence(entity.getMedia()));
    }

    public static ParcelPersistenceEntity toPersistenceFromDomain(Parcel parcel) {
        ParcelPersistenceEntity entity = new ParcelPersistenceEntity();
        entity.setId(parcel.getId().value());
        entity.setOwnerId(parcel.getOwnerId().value());
        entity.setName(parcel.getName().value());
        entity.setStatus(parcel.getStatus());
        entity.setArea(parcel.getArea().value());
        entity.setLocation(parcel.getLocation().value());
        entity.setSoilType(parcel.getSoilType().value());
        entity.setCampaignCost(parcel.getCampaignCost().value());
        entity.setSchedule(toPersistenceFromDomain(parcel.getSchedule()));
        entity.setCrops(parcel.getCrops().values());
        entity.setMedia(toPersistenceFromDomain(parcel.getMedia()));
        return entity;
    }

    private static CampaignSchedule toDomainFromPersistence(CampaignSchedulePersistenceEmbeddable value) {
        if (value == null) {
            return new CampaignSchedule(0, "");
        }
        return new CampaignSchedule(value.getDurationMonths() == null ? 0 : value.getDurationMonths(), value.getStartMonth());
    }

    private static CampaignSchedulePersistenceEmbeddable toPersistenceFromDomain(CampaignSchedule value) {
        return value == null ? null : new CampaignSchedulePersistenceEmbeddable(value.durationMonths(), value.startMonth());
    }

    private static ParcelMedia toDomainFromPersistence(ParcelMediaPersistenceEmbeddable value) {
        return value == null ? new ParcelMedia("", null) : new ParcelMedia(value.getImageUrl(), value.getImages());
    }

    private static ParcelMediaPersistenceEmbeddable toPersistenceFromDomain(ParcelMedia value) {
        return value == null ? null : new ParcelMediaPersistenceEmbeddable(value.imageUrl(), value.images());
    }
}
