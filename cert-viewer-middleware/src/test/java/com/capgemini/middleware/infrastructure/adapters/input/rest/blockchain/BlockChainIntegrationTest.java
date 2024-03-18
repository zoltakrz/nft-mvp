package com.capgemini.middleware.infrastructure.adapters.input.rest.blockchain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlockChainIntegrationTest {

    @Autowired
    private MockMvc mvc;

    List<Map<String,Object>> allData = ExpectedBlockChainTestData.getAllData();
    Map<String,Object> token299 = ExpectedBlockChainTestData.getData("token299");
    Map<String,Object> token300 = ExpectedBlockChainTestData.getData("token300");


    @Test
    @Disabled
    void getCertificatesForEmail() throws Exception {
        String email = "joe.fortestingmiddleman@capgemini.com";
        String tokenID = "tokenID";
        String firstName = "firstName";
        String lastName = "lastName";
        String certType = "certType";
        String certLevel = "certLevel";
        mvc.perform(get("/v1/certificates/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("certificates", hasSize(allData.size())))

                .andExpect(jsonPath("certificates[0]." + tokenID,is(token299.get(tokenID))))
                .andExpect(jsonPath("certificates[0]." + firstName,is(token299.get(firstName))))
                .andExpect(jsonPath("certificates[0]." + lastName,is(token299.get(lastName))))
                .andExpect(jsonPath("certificates[0]." + certType,is(token299.get(certType))))
                .andExpect(jsonPath("certificates[0]." + certLevel,is(token299.get(certLevel))))

                .andExpect(jsonPath("certificates[1]." + tokenID,is(token300.get(tokenID))))
                .andExpect(jsonPath("certificates[1]." + firstName,is(token300.get(firstName))))
                .andExpect(jsonPath("certificates[1]." + lastName,is(token300.get(lastName))))
                .andExpect(jsonPath("certificates[1]." + certType,is(token300.get(certType))))
                .andExpect(jsonPath("certificates[1]." + certLevel,is(token300.get(certLevel))));
    }
}