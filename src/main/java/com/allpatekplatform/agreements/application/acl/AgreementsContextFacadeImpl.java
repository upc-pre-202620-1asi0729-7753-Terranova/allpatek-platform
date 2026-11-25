package com.allpatekplatform.agreements.application.acl;

import com.allpatekplatform.agreements.domain.repositories.AgreementRepository;
import com.allpatekplatform.agreements.interfaces.acl.AgreementsContextFacade;
import org.springframework.stereotype.Service;

/**
 * Application-layer implementation of the Agreements ACL facade.
 */
@Service
public class AgreementsContextFacadeImpl implements AgreementsContextFacade {

    private final AgreementRepository agreementRepository;

    public AgreementsContextFacadeImpl(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    @Override
    public Long fetchMerchantProfileId(String agreementId) {
        if (agreementId == null || agreementId.isBlank()) {
            return 0L;
        }
        return agreementRepository.findById(agreementId)
                .map(agreement -> agreement.getMerchantProfileId().value())
                .filter(profileId -> profileId != null && profileId > 0L)
                .orElse(0L);
    }
}
