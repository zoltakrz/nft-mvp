package com.capgemini.middleware.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

@Getter
@EqualsAndHashCode
@ToString
public class NFTCertificateDTO {
    private final BigInteger tokenIndex;
    private final String firstName;
    private final String lastName;
    private final String hashedEmail;
    @NonNull
    private final CertType certType;
    @NonNull
    private final CertLevel certLevel;

    protected NFTCertificateDTO(String firstName, String lastName, @NotNull CertType certType, @NotNull CertLevel certLevel, BigInteger index, String hashedEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.certType = certType;
        this.certLevel = certLevel;
        this.tokenIndex = index;
        this.hashedEmail = hashedEmail;
    }
}
