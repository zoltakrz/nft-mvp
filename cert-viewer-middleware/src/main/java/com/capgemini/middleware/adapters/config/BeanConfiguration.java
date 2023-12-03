package com.capgemini.middleware.adapters.config;

import com.capgemini.middleware.domain.blockchain.BlockchainConnector;
import com.capgemini.middleware.domain.blockchain.SmartContractFacade;
import com.capgemini.middleware.domain.service.NFTCertificateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public BlockchainConnector blockchainConnector() {
        return new BlockchainConnector();
    }

    @Bean
    public SmartContractFacade smartContractFacade() {
        return new SmartContractFacade(blockchainConnector());
    }

    @Bean
    public NFTCertificateService NFTCertificateService() {
        return new NFTCertificateService(smartContractFacade());
    }
}
