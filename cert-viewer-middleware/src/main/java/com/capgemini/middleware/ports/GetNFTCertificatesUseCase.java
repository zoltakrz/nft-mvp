package com.capgemini.middleware.ports;

import com.capgemini.middleware.domain.blockchain.CertificateSnapshot;

public interface GetNFTCertificatesUseCase {
    CertificateSnapshot getNFTCertificatesForEmail(String hashedEmail);
    CertificateSnapshot getNFTCertificatesForAddressOfOwner(String addressOfOwner);
    CertificateSnapshot getAllNFTCertificates();

}
