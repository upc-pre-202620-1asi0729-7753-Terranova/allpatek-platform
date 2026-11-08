package com.allpatekplatform.parcels.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.parcels.domain.model.aggregates.Parcel;
import com.allpatekplatform.parcels.domain.repositories.ParcelRepository;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.assemblers.ParcelPersistenceAssembler;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.repositories.ParcelPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParcelRepositoryImpl implements ParcelRepository {

    private final ParcelPersistenceRepository persistenceRepository;

    public ParcelRepositoryImpl(ParcelPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Parcel save(Parcel parcel) {
        return ParcelPersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(ParcelPersistenceAssembler.toPersistenceFromDomain(parcel)));
    }

    @Override
    public Optional<Parcel> findById(String id) {
        return persistenceRepository.findById(id).map(ParcelPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsById(String id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public List<Parcel> findAll() {
        return persistenceRepository.findAll().stream()
                .map(ParcelPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        persistenceRepository.deleteById(id);
    }
}
