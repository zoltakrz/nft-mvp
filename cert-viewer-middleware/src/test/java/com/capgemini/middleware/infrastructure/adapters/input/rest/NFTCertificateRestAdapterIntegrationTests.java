package com.capgemini.middleware.infrastructure.adapters.input.rest;

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
class NFTCertificateRestAdapterIntegrationTests {

    @Autowired
    private MockMvc mvc;

    List<Map<String,Object>> allData = ExpectedTestData.getAllData();
    Map<String,Object> token291 = ExpectedTestData.getData("token291");
    Map<String,Object> token292 = ExpectedTestData.getData("token292");


    @Test
    void getCertificatesForEmail() throws Exception {
        String email = "joe.fortestingmiddleman@capgemini.com";
        String tokenIndex = "tokenIndex";
        String firstName = "firstName";
        String lastName = "lastName";
        String certType = "certType";
        String certLevel = "certLevel";
        mvc.perform(get("/v1/certificates/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$", hasSize(allData.size())))

                .andExpect(jsonPath("$[0]." + tokenIndex,is(token291.get(tokenIndex))))
                .andExpect(jsonPath("$[0]." + firstName,is(token291.get(firstName))))
                .andExpect(jsonPath("$[0]." + lastName,is(token291.get(lastName))))
                .andExpect(jsonPath("$[0]." + certType,is(token291.get(certType))))
                .andExpect(jsonPath("$[0]." + certLevel,is(token291.get(certLevel))))

                .andExpect(jsonPath("$[1]." + tokenIndex,is(token292.get(tokenIndex))))
                .andExpect(jsonPath("$[1]." + firstName,is(token292.get(firstName))))
                .andExpect(jsonPath("$[1]." + lastName,is(token292.get(lastName))))
                .andExpect(jsonPath("$[1]." + certType,is(token292.get(certType))))
                .andExpect(jsonPath("$[1]." + certLevel,is(token292.get(certLevel))));
    }
}