package com.allpatekplatform.parcels.interfaces.rest.transform;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.model.commands.CreateParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.UpdateParcelCommand;
import com.allpatekplatform.parcels.interfaces.rest.resources.ParcelResource;

public final class ParcelResourceAssembler {

    private ParcelResourceAssembler() {
    }

    public static ParcelResource toResourceFromEntity(Parcel parcel) {
        return new ParcelResource(
                parcel.getId().value(),
                parcel.getOwnerId().value(),
                parcel.getName().value(),
                parcel.getStatus().toApiValue(),
                parcel.getArea().value(),
                parcel.getLocation().value(),
                parcel.getSoilType().value(),
                parcel.getCampaignCost().value(),
                parcel.getSchedule().durationMonths(),
                parcel.getSchedule().startMonth(),
                parcel.getCrops().values(),
                parcel.getMedia().imageUrl(),
                parcel.getMedia().images());
    }

    public static CreateParcelCommand toCreateCommand(ParcelResource resource) {
        return new CreateParcelCommand(
                resource.id(),
                resource.ownerId(),
                resource.name(),
                resource.status(),
                resource.area(),
                resource.location(),
                resource.soilType(),
                resource.campaignCost(),
                resource.durationMonths() == null ? 0 : resource.durationMonths(),
                resource.startMonth(),
                resource.crops(),
                resource.imageUrl(),
                resource.images());
    }

    public static UpdateParcelCommand toUpdateCommand(String id, ParcelResource resource) {
        return new UpdateParcelCommand(
                id,
                resource.ownerId(),
                resource.name(),
                resource.status(),
                resource.area(),
                resource.location(),
                resource.soilType(),
                resource.campaignCost(),
                resource.durationMonths() == null ? 0 : resource.durationMonths(),
                resource.startMonth(),
                resource.crops(),
                resource.imageUrl(),
                resource.images());
    }
}
