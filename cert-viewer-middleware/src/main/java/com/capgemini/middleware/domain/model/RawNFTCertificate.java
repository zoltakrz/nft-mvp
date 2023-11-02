package com.capgemini.middleware.domain.model;

import java.math.BigInteger;

public record RawNFTCertificate(
        String hashedEmail,
        String firstName,
        String lastName,
        String certType,
        String certLevel) {
    public NFTCertificateDTO toNFTCertificateDTO(BigInteger index) {
        final CertType certType = mapToCertType(certType());
        final CertLevel certLevel = mapToCertLevel(certLevel());
        return new NFTCertificateDTO(firstName, lastName, certType, certLevel, index);
    }

    private CertLevel mapToCertLevel(String input) {
        return switch (input.toLowerCase()) {
            case "foundation" -> CertLevel.FOUNDATION;
            case "0" -> CertLevel.ZERO;
            case "1" -> CertLevel.ONE;
            case "1p" -> CertLevel.ONE_P;
            case "2" -> CertLevel.TWO;
            case "2p" -> CertLevel.TWO_P;
            case "3" -> CertLevel.THREE;
            case "3p" -> CertLevel.THREE_P;
            case "4" -> CertLevel.FOUR;
            default -> CertLevel.NOT_SUPPORTED;
        };
    }

    private CertType mapToCertType(String input) {
        return switch (input.toLowerCase()) {
            case "architect" -> CertType.ARCHITECT;
            case "engagement manager" -> CertType.ENGAGEMENT_MANAGER;
            default -> CertType.NOT_SUPPORTED;
        };
    }
}
