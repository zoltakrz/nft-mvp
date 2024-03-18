package com.capgemini.middleware.domain.blockchain;

import java.util.UUID;

import static com.capgemini.middleware.domain.model.MockedNFTCertificateDTO.mockedCertificates;

public class MockSmartContractFacade implements SmartContractFacadeInterface {

    private static final String TIMESTAMP = UUID.randomUUID().toString();


    @Override
    public void updateCache() {
    }

    @Override
    public CertificateSnapshot getNFTCertificatesForEmail(String hashedEmail) {
        var filteredCertificates = mockedCertificates.stream()
                .filter(cert -> cert.getHashedEmail().equals(hashedEmail))
                .toList();

        return new CertificateSnapshot(filteredCertificates, TIMESTAMP);
    }

    @Override
    public CertificateSnapshot getAllNFTCertificates() {
        return new CertificateSnapshot(mockedCertificates, TIMESTAMP);
    }

    @Override
    public CertificateSnapshot getNFTCertificatesForAddressOfOwner(String addressOfOwner) {
        return getAllNFTCertificates();
    }
}
