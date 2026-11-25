package com.allpatekplatform.escrow.application.internal.outboundservices.acl;

import com.allpatekplatform.agreements.interfaces.acl.AgreementsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL service used by the Escrow bounded context to resolve the merchant
 * of an agreement without depending on the agreements domain model.
 */
@Service
public class ExternalAgreementService {

    private final AgreementsContextFacade agreementsContextFacade;

    public ExternalAgreementService(AgreementsContextFacade agreementsContextFacade) {
        this.agreementsContextFacade = agreementsContextFacade;
    }

    public Optional<Long> fetchMerchantProfileId(String agreementId) {
        Long profileId = agreementsContextFacade.fetchMerchantProfileId(agreementId);
        return profileId == null || profileId == 0L ? Optional.empty() : Optional.of(profileId);
    }
}
