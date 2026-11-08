package com.allpatekplatform.shared.infrastructure.persistence.jpa.entities;

import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.embeddables.CompanyDetailsPersistenceEmbeddable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class ProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;

    @Column(length = 40)
    private String phone;

    @Column(length = 40)
    private String document;

    @Column(length = 240)
    private String address;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(name = "email_address", nullable = false, unique = true, length = 160)
    private EmailAddress emailAddress;

    @Embedded
    private CompanyDetailsPersistenceEmbeddable companyDetails;
}
