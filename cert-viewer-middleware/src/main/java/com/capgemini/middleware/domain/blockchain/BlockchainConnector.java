package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.blockchain.generated.CertToken;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.model.RawNFTCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainConnector {
    private static final String SMART_CONTRACT_ADDRESS = "0x70C0B60E84BDeeC72E855325521d7D51F105239f";

    public Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String hashedEmail, Web3j web3j) {
        List<BigInteger> tokenIDsForEmail = getTokenIDsForEmail(hashedEmail, web3j);
        return tokenIDsForEmail.stream()
                .map(tokenID -> getCert(tokenID, web3j))
                .collect(Collectors.toList());
    }

    private List<BigInteger> getTokenIDsForEmail(String hashedEmail, Web3j web3j) {
        try {
            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, Credentials.create(SMART_CONTRACT_ADDRESS), new DefaultGasProvider());

            return contract.certificantTokenIds(hashedEmail).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BigInteger getTotalSupply(Web3j web3j) {
        try {
            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, Credentials.create(SMART_CONTRACT_ADDRESS), new DefaultGasProvider());

            return contract.totalSupply().send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getOwner(BigInteger index, Web3j web3j) {
        try {
            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, Credentials.create(SMART_CONTRACT_ADDRESS), new DefaultGasProvider());

            return contract.ownerOf(index).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NFTCertificateDTO getCert(BigInteger index, Web3j web3j) {
        try {
            Credentials credentials = Credentials.create(SMART_CONTRACT_ADDRESS);

            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());

            String encodedCert = contract.tokenURI(index)
                    .send()
                    .replace("data:application/json;base64,", "");

            String decodedCert = new String(Base64.getDecoder().decode(encodedCert));

            RawNFTCertificate rawNFTCertificate = new ObjectMapper().readValue(decodedCert, RawNFTCertificate.class);

            return rawNFTCertificate.toNFTCertificateDTO(index);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
