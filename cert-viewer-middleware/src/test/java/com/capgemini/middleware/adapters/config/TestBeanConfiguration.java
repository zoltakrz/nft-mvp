package com.capgemini.middleware.adapters.config;

import com.capgemini.middleware.domain.blockchain.MockSmartContractFacade;
import com.capgemini.middleware.domain.blockchain.SmartContractFacadeInterface;
import com.capgemini.middleware.domain.service.NFTCertificateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestBeanConfiguration {
    @Bean
    @Profile(Profiles.INTEGRATION_PROFILE)
    public SmartContractFacadeInterface smartContractFacade() {
        return new MockSmartContractFacade();
    }
}
