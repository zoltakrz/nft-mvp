package com.capgemini.middleware.domain.service;

import com.capgemini.middleware.domain.blockchain.BlockchainConnector;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.model.RawNFTCertificate;
import com.capgemini.middleware.domain.utills.Keccak256Encoder;
import com.capgemini.middleware.ports.input.GetNFTCertificatesUseCase;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class NFTCertificateService implements GetNFTCertificatesUseCase {

    private final BlockchainConnector blockchainConnector;

    @Override
    public Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String email) {
        String hashedEmail = Keccak256Encoder.encode(email.toLowerCase());
        return blockchainConnector.getNFTCertificatesForEmail(hashedEmail);
    }
}
