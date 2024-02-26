package com.capgemini.middleware.domain.service;

import com.capgemini.middleware.domain.blockchain.CertificateSnapshot;
import com.capgemini.middleware.domain.blockchain.SmartContractFacade;
import com.capgemini.middleware.domain.utills.Keccak256Encoder;
import com.capgemini.middleware.ports.GetNFTCertificatesUseCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NFTCertificateService implements GetNFTCertificatesUseCase {

    private final SmartContractFacade smartContractFacade;

    @Override
    public CertificateSnapshot getNFTCertificatesForEmail(String email) {
        final String hashedEmail = Keccak256Encoder.encode(email.toLowerCase());
        return smartContractFacade.getNFTCertificatesForEmail(hashedEmail);
    }

    @Override
    public CertificateSnapshot getNFTCertificatesForAddressOfOwner(String addressOfOwner) {
        return smartContractFacade.getNFTCertificatesForAddressOfOwner(addressOfOwner);
    }

    @Override
    public CertificateSnapshot getAllNFTCertificates() {
        return smartContractFacade.getAllNFTCertificates();
    }

    @Override
    public void updateCache() {
        smartContractFacade.updateCache();
    }
}
