package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.blockchain.generated.CertToken;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.model.RawNFTCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.util.Base64;
import java.util.List;

public class BlockchainConnector {

    List<BigInteger> getTokenIDsForEmail(String hashedEmail, CertToken smartContract) {
        try {
            return smartContract.certificantTokenIds(hashedEmail).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    BigInteger getTotalSupplyOfBlockChainIndexes(CertToken smartContract) {
        try {
            return smartContract.totalSupply().send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String getAddressOfOwner(BigInteger tokenID, CertToken smartContract) {
        try {
            return smartContract.ownerOf(tokenID).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    BigInteger getTokenIDForBlockChainIndex(BigInteger blockChainIndex, CertToken smartContract) {
        try {
            return smartContract.tokenByIndex(blockChainIndex).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    NFTCertificateDTO getCertificateForTokenID(BigInteger tokenID, CertToken smartContract) {
        try {
            String encodedCert = smartContract.tokenURI(tokenID)
                    .send()
                    .replace("data:application/json;base64,", "");

            String decodedCert = new String(Base64.getDecoder().decode(encodedCert));

            RawNFTCertificate rawNFTCertificate = new ObjectMapper().readValue(decodedCert, RawNFTCertificate.class);

            return rawNFTCertificate.toNFTCertificateDTO(tokenID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
