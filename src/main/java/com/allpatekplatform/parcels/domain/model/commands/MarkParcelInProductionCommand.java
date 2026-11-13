package com.allpatekplatform.parcels.domain.model.commands;

/**
 * Marks a parcel as in production after its campaign agreement is fully signed.
 */
public record MarkParcelInProductionCommand(String parcelId) {
}
