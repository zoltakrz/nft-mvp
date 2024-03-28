package com.capgemini.middleware.adapters.config;

import com.capgemini.middleware.domain.blockchain.BlockchainConnector;
import com.capgemini.middleware.domain.blockchain.SmartContractFacade;
import com.capgemini.middleware.domain.blockchain.SmartContractFacadeInterface;
import com.capgemini.middleware.domain.service.NFTCertificateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class BeanConfiguration {
    @Bean
    public BlockchainConnector blockchainConnector() {
        return new BlockchainConnector();
    }

    @Bean
    public NFTCertificateService NFTCertificateService() {
        return new NFTCertificateService(smartContractFacade());
    }

    @Bean
    @Profile(Profiles.EXCEPT_INTEGRATION_PROFILE)
    public SmartContractFacadeInterface smartContractFacade() {
        return new SmartContractFacade(blockchainConnector());
    }
}
