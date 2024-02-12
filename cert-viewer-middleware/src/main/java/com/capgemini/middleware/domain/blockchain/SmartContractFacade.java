package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.blockchain.generated.CertToken;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.net.ConnectException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class SmartContractFacade {

    private final BlockchainConnector blockchainConnector;

    private BlockChainCache blockChainCache;

    public SmartContractFacade(BlockchainConnector blockchainConnector) {
        this.blockchainConnector = blockchainConnector;
        this.blockChainCache = getCache();
    }

    @Scheduled(cron = "* 0 * * * *") // Cron expression for running every hour
    public void updateCache() {
        BlockChainCache newCache = getCache();
        log.info("Downloaded new cache with timestamp: {}", newCache.updateTime());
        this.blockChainCache = newCache;
    }

    public CertificateSnapshot getNFTCertificatesForEmail(String hashedEmail) {
        CertToken smartContract = getSmartContract();
        List<BigInteger> tokenIDsForEmail = blockchainConnector.getTokenIDsForEmail(hashedEmail, smartContract);
        List<NFTCertificateDTO> certificates = tokenIDsForEmail.stream()
                .map(tokenID -> blockchainConnector.getCertificateForTokenID(tokenID, smartContract))
                .collect(Collectors.toList());

        return new CertificateSnapshot(certificates, OffsetDateTime.now(ZoneOffset.UTC) + " UTC TimeZone");
    }

    public CertificateSnapshot getAllNFTCertificates() {
        List<NFTCertificateDTO> nftCertificateDTOS = blockChainCache.entities().stream()
                .map(NFTCertificateCacheEntity::nftCertificateDTO).toList();

        return new CertificateSnapshot(nftCertificateDTOS, blockChainCache.updateTime());
    }

    public CertificateSnapshot getNFTCertificatesForAddressOfOwner(String addressOfOwner) {
        List<NFTCertificateDTO> nftCertificateDTOS = blockChainCache.entities().stream()
                .filter(entity -> entity.ownerAddress().equals(addressOfOwner))
                .map(NFTCertificateCacheEntity::nftCertificateDTO).toList();

        return new CertificateSnapshot(nftCertificateDTOS, blockChainCache.updateTime());
    }

    private BlockChainCache getCache() {
        CertToken smartContract = getSmartContract();

        BigInteger totalSupplyOfBlockChainIndexes = blockchainConnector.getTotalSupplyOfBlockChainIndexes(smartContract);

        Set<NFTCertificateCacheEntity> entities = Arrays.stream(IntStream.range(0, totalSupplyOfBlockChainIndexes.intValue()).toArray())
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .map(index -> blockchainConnector.getTokenIDForBlockChainIndex(index, smartContract))
                .map(tokenID -> {
                    NFTCertificateDTO certificateForTokenID = blockchainConnector.getCertificateForTokenID(tokenID, smartContract);
                    String addressOfOwner = blockchainConnector.getAddressOfOwner(tokenID, smartContract);
                    return new NFTCertificateCacheEntity(certificateForTokenID, addressOfOwner);
                }).collect(Collectors.toSet());

        OffsetDateTime timestampInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        return new BlockChainCache(entities, timestampInUTC + " UTC TimeZone");
    }

    private static CertToken getSmartContract() {
        Web3j web3j;
        try {
            web3j = Web3Factory.getConnectedWeb3j();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        return Web3Factory.getSmartContract(web3j);
    }
}
