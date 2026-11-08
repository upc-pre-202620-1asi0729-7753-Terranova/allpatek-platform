package com.allpatekplatform.parcels.domain.model.aggregates;

import com.allpatekplatform.parcels.domain.model.commands.CreateParcelCommand;
import com.allpatekplatform.parcels.domain.model.commands.UpdateParcelCommand;
import com.allpatekplatform.parcels.domain.model.valueobjects.CampaignCost;
import com.allpatekplatform.parcels.domain.model.valueobjects.CampaignSchedule;
import com.allpatekplatform.parcels.domain.model.valueobjects.Crops;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelArea;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelLocation;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelMedia;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelName;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelStatus;
import com.allpatekplatform.parcels.domain.model.valueobjects.SoilType;
import com.allpatekplatform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import lombok.Getter;

import java.util.Objects;

/**
 * Parcel aggregate root. Persistence lives in {@code ParcelPersistenceEntity}.
 */
@Getter
public class Parcel extends AbstractDomainAggregateRoot<Parcel> {

    private ParcelId id;
    private ProfileId ownerId;
    private ParcelName name;
    private ParcelStatus status;
    private ParcelArea area;
    private ParcelLocation location;
    private SoilType soilType;
    private CampaignCost campaignCost;
    private CampaignSchedule schedule;
    private Crops crops;
    private ParcelMedia media;

    public Parcel(
            ParcelId id,
            ProfileId ownerId,
            ParcelName name,
            ParcelStatus status,
            ParcelArea area,
            ParcelLocation location,
            SoilType soilType,
            CampaignCost campaignCost,
            CampaignSchedule schedule,
            Crops crops,
            ParcelMedia media
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.ownerId = ownerId == null ? new ProfileId(null) : ownerId;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.status = status == null ? ParcelStatus.available : status;
        this.area = area == null ? new ParcelArea("") : area;
        this.location = location == null ? new ParcelLocation("") : location;
        this.soilType = soilType == null ? new SoilType("") : soilType;
        this.campaignCost = campaignCost == null ? new CampaignCost("") : campaignCost;
        this.schedule = schedule == null ? new CampaignSchedule(0, "") : schedule;
        this.crops = crops == null ? Crops.of(null) : crops;
        this.media = media == null ? new ParcelMedia("", null) : media;
    }

    public Parcel(CreateParcelCommand command) {
        this(
                new ParcelId(command.id()),
                new ProfileId(command.ownerId()),
                new ParcelName(command.name()),
                ParcelStatus.from(command.status()),
                new ParcelArea(command.area()),
                new ParcelLocation(command.location()),
                new SoilType(command.soilType()),
                new CampaignCost(command.campaignCost()),
                new CampaignSchedule(command.durationMonths(), command.startMonth()),
                Crops.of(command.crops()),
                new ParcelMedia(command.imageUrl(), command.images()));
    }

    public Parcel(UpdateParcelCommand command) {
        this(
                new ParcelId(command.id()),
                new ProfileId(command.ownerId()),
                new ParcelName(command.name()),
                ParcelStatus.from(command.status()),
                new ParcelArea(command.area()),
                new ParcelLocation(command.location()),
                new SoilType(command.soilType()),
                new CampaignCost(command.campaignCost()),
                new CampaignSchedule(command.durationMonths(), command.startMonth()),
                Crops.of(command.crops()),
                new ParcelMedia(command.imageUrl(), command.images()));
    }

    public void update(UpdateParcelCommand command) {
        Parcel updated = new Parcel(command);
        this.ownerId = updated.ownerId;
        this.name = updated.name;
        this.status = updated.status;
        this.area = updated.area;
        this.location = updated.location;
        this.soilType = updated.soilType;
        this.campaignCost = updated.campaignCost;
        this.schedule = updated.schedule;
        this.crops = updated.crops;
        this.media = updated.media;
    }
}
