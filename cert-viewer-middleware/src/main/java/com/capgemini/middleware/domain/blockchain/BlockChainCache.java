package com.capgemini.middleware.domain.blockchain;

import java.util.Collection;

public record BlockChainCache(Collection<NFTCertificateCacheEntity> entities, String updateTime) {
}
