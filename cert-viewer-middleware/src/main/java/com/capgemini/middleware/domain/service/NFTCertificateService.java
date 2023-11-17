package com.capgemini.middleware.domain.service;

import com.capgemini.middleware.domain.blockchain.BlockchainConnector;
import com.capgemini.middleware.domain.blockchain.Web3Factory;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.utills.Keccak256Encoder;
import com.capgemini.middleware.ports.GetNFTCertificatesUseCase;
import lombok.AllArgsConstructor;
import org.web3j.protocol.Web3j;

import java.net.ConnectException;
import java.util.Collection;

@AllArgsConstructor
public class NFTCertificateService implements GetNFTCertificatesUseCase {

    private final BlockchainConnector blockchainConnector;

    @Override
    public Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String email) {
        String hashedEmail = Keccak256Encoder.encode(email.toLowerCase());
        Web3j web3j;
        try {
            web3j = Web3Factory.getConnectedWeb3j();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        return blockchainConnector.getNFTCertificatesForEmail(hashedEmail, web3j);
    }
}
