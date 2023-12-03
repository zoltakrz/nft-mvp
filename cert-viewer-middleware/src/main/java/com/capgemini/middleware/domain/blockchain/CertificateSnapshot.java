package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;

import java.util.Collection;

public record CertificateSnapshot(Collection<NFTCertificateDTO> certificates, String lastUpdateTimestamp) {
}
