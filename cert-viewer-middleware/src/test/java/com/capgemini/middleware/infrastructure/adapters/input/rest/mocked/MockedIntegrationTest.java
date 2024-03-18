package com.capgemini.middleware.infrastructure.adapters.input.rest.mocked;

import com.capgemini.middleware.adapters.config.Profiles;
import com.capgemini.middleware.adapters.contract.ContractCertificates;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.List;

import static com.capgemini.middleware.domain.model.MockedNFTCertificateDTO.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.INTEGRATION_PROFILE)
public class MockedIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnAllCertificates() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/v1/certificates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andReturn();

        List<ContractCertificates> certificateDTOS = extractCertificates(mvcResult);

        assertEquals(3, certificateDTOS.size());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ARCHITECT_CERTIFICATE_WITH_FIRST_EMAIL.getTokenID())).count());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_SECOND_EMAIL.getTokenID())).count());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_TEST_EMAIL.getTokenID())).count());
    }

    @Test
    void shouldReturnAllCertificatesWithoutTestOnes() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/v1/certificatesWithoutTestOnes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andReturn();

        List<ContractCertificates> certificateDTOS = extractCertificates(mvcResult);

        assertEquals(2, certificateDTOS.size());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ARCHITECT_CERTIFICATE_WITH_FIRST_EMAIL.getTokenID())).count());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_SECOND_EMAIL.getTokenID())).count());
    }

    @Test
    void shouldReturnAllArchitectCertificates() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/v1/certificates?certType=ARCHITECT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andReturn();

        List<ContractCertificates> certificateDTOS = extractCertificates(mvcResult);

        assertEquals(1, certificateDTOS.size());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ARCHITECT_CERTIFICATE_WITH_FIRST_EMAIL.getTokenID())).count());
    }

    @Test
    void shouldReturnAllEMCertificates() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/v1/certificates?certType=ENGAGEMENT_MANAGEMENT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andReturn();

        List<ContractCertificates> certificateDTOS = extractCertificates(mvcResult);

        assertEquals(2, certificateDTOS.size());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_TEST_EMAIL.getTokenID())).count());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_SECOND_EMAIL.getTokenID())).count());
    }

    @Test
    void shouldReturnCertificatesForCertainEmail() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/v1/certificates/" + testEmail))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andReturn();

        List<ContractCertificates> certificateDTOS = extractCertificates(mvcResult);

        assertEquals(1, certificateDTOS.size());
        assertEquals(1, certificateDTOS.stream().filter(cert -> cert.getTokenID().equals(ENGAGEMENT_MANAGEMENT_CERTIFICATE_WITH_TEST_EMAIL.getTokenID())).count());
    }

    private static List<ContractCertificates> extractCertificates(MvcResult mvcResult) {
        String content = new String(mvcResult.getResponse().getContentAsByteArray());

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(content, JsonObject.class);
        JsonArray certificates = json.get("certificates").getAsJsonArray();
        Type listType = new TypeToken<List<ContractCertificates>>() {
        }.getType();
        return gson.fromJson(certificates, listType);
    }
}
