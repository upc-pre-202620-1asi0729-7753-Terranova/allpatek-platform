package com.allpatekplatform.agreements.interfaces.acl;

/**
 * ACL facade that exposes Agreements bounded context capabilities to other contexts.
 */
public interface AgreementsContextFacade {

    Long fetchMerchantProfileId(String agreementId);
}
