package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;

public record NFTCertificateCacheEntity(NFTCertificateDTO nftCertificateDTO, String ownerAddress) {
}
