package com.capgemini.middleware.adapters.config;

import com.capgemini.middleware.domain.blockchain.BlockchainConnector;
import com.capgemini.middleware.domain.service.NFTCertificateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public NFTCertificateService NFTCertificateService() {
        return new NFTCertificateService(new BlockchainConnector());
    }

    @Bean
    public BlockchainConnector blockchainConnector() {
        return new BlockchainConnector();
    }
}
