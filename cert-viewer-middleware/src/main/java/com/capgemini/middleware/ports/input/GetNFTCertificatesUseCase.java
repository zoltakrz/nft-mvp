package com.capgemini.middleware.ports.input;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.model.RawNFTCertificate;

import java.util.Collection;

public interface GetNFTCertificatesUseCase {
    Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String hashedEmail);
}
