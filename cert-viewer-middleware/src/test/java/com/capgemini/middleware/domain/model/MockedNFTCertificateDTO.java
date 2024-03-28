package com.capgemini.middleware.domain.model;

import com.capgemini.middleware.domain.utills.Keccak256Encoder;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MockedNFTCertificateDTO {
    public static final String firstEmail = "first@email.com";
    public static final String secondEmail = "second@email.com";
    public static final String testEmail = "joe.fortesting@capgemini.com";
    public static final NFTCertificateDTO ARCHITECT_CERTIFICATE_WITH_FIRST_EMAIL = new NFTCertificateDTO(
            getRandomString(),
            getRandomString(),
            CertType.ARCHITECT,
            CertLevel.ONE,
            BigInteger.ONE,
            Keccak256Encoder.encode(firstEmail)
    );

    public static final NFTCertificateDTO ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_SECOND_EMAIL = new NFTCertificateDTO(
            getRandomString(),
            getRandomString(),
            CertType.ENGAGEMENT_MANAGEMENT,
            CertLevel.ONE,
            BigInteger.TWO,
            Keccak256Encoder.encode(secondEmail)
    );

    public static final NFTCertificateDTO ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_TEST_EMAIL = new NFTCertificateDTO(
            getRandomString(),
            getRandomString(),
            CertType.ENGAGEMENT_MANAGEMENT,
            CertLevel.ONE,
            BigInteger.valueOf(3),
            Keccak256Encoder.encode(testEmail)
    );


    public static final Collection<NFTCertificateDTO> mockedCertificates = List.of(
            ARCHITECT_CERTIFICATE_WITH_FIRST_EMAIL,
            ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_SECOND_EMAIL,
            ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_TEST_EMAIL
    );

    private static String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
