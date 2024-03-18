package com.capgemini.middleware.domain.blockchain;

public interface SmartContractFacadeInterface {
    void updateCache();
    CertificateSnapshot getNFTCertificatesForEmail(String hashedEmail);
    CertificateSnapshot getAllNFTCertificates();
    CertificateSnapshot getNFTCertificatesForAddressOfOwner(String addressOfOwner);
}
