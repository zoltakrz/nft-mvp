package com.capgemini.middleware.adapters.contract;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@ToString
public class ContractCertificates {
    private final BigInteger tokenIndex;
    private final String firstName;
    private final String lastName;
    private final String certType;
    private final String certLevel;

    public ContractCertificates(NFTCertificateDTO nftCertificateDTO) {
        this.tokenIndex = nftCertificateDTO.getTokenIndex();
        this.firstName = nftCertificateDTO.getFirstName();
        this.lastName = nftCertificateDTO.getLastName();
        this.certType = nftCertificateDTO.getCertType().getValue();
        this.certLevel = nftCertificateDTO.getCertLevel().getValue();
    }
}
