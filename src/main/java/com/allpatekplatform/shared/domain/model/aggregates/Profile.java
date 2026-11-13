package com.allpatekplatform.shared.domain.model.aggregates;

import com.allpatekplatform.shared.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.shared.domain.model.valueobjects.CompanyDetails;
import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.shared.domain.model.valueobjects.IdentityDocument;
import com.allpatekplatform.shared.domain.model.valueobjects.PersonName;
import com.allpatekplatform.shared.domain.model.valueobjects.PhoneNumber;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import com.allpatekplatform.shared.domain.model.valueobjects.StreetAddress;
import lombok.Getter;

import java.util.Objects;

/**
 * Profile aggregate root. Persistence mapping lives in {@code ProfilePersistenceEntity}.
 */
@Getter
public class Profile extends AbstractDomainAggregateRoot<Profile> {

    private ProfileId id;
    private PersonName fullName;
    private PhoneNumber phone;
    private IdentityDocument document;
    private StreetAddress address;
    private EmailAddress emailAddress;
    private CompanyDetails company;

    public Profile(
            ProfileId id,
            PersonName fullName,
            PhoneNumber phone,
            IdentityDocument document,
            StreetAddress address,
            EmailAddress emailAddress,
            CompanyDetails company
    ) {
        this.id = id;
        this.fullName = Objects.requireNonNull(fullName, "name must not be null");
        this.phone = phone == null ? new PhoneNumber("") : phone;
        this.document = document == null ? new IdentityDocument("") : document;
        this.address = address == null ? new StreetAddress("") : address;
        this.emailAddress = Objects.requireNonNull(emailAddress, "emailAddress must not be null");
        this.company = company == null ? new CompanyDetails("", "", "") : company;
    }

    public void update(UpdateProfileCommand command) {
        this.fullName = new PersonName(command.fullName());
        this.phone = new PhoneNumber(command.phone());
        this.document = new IdentityDocument(command.document());
        this.address = new StreetAddress(command.address());
        this.emailAddress = new EmailAddress(command.email());
        this.company = new CompanyDetails(command.company(), command.ruc(), command.activity());
    }

    public String getEmail() {
        return emailAddress.address();
    }
}
