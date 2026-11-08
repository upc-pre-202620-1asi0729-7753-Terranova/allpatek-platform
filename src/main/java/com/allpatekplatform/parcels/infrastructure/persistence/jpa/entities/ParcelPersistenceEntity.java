package com.allpatekplatform.parcels.infrastructure.persistence.jpa.entities;

import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelStatus;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables.CampaignSchedulePersistenceEmbeddable;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables.ParcelMediaPersistenceEmbeddable;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.converters.StringListConverter;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.entities.StringIdentifiablePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parcels")
public class ParcelPersistenceEntity extends StringIdentifiablePersistenceEntity {

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(nullable = false, length = 160)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ParcelStatus status;

    @Column(length = 40)
    private String area;

    @Column(length = 160)
    private String location;

    @Column(name = "soil_type", length = 80)
    private String soilType;

    @Column(name = "campaign_cost", length = 40)
    private String campaignCost;

    @Embedded
    private CampaignSchedulePersistenceEmbeddable schedule;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "LONGTEXT")
    private List<String> crops;

    @Embedded
    private ParcelMediaPersistenceEmbeddable media;
}
