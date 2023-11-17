package com.capgemini.middleware.ports;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;

import java.util.Collection;

public interface GetNFTCertificatesUseCase {
    Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String hashedEmail);
}
