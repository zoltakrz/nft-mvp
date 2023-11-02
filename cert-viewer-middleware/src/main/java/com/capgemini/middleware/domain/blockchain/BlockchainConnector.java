package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.blockchain.generated.CertToken;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.model.RawNFTCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainConnector {

    private static final String MINTED_TOKEN_URL_PREFIX = "https://apigateway-webapp.azurewebsites.net/blockscout/token/0x70c0b60e84bdeec72e855325521d7d51f105239f/instance/";
    private static final String WEB_SERVICE_ADDRESS = "wss://apigateway-webapp.azurewebsites.net";
    private static final String SMART_CONTRACT_ADDRESS = "0x70C0B60E84BDeeC72E855325521d7D51F105239f";

    public Collection<NFTCertificateDTO> getNFTCertificatesForEmail(String hashedEmail) {
        List<BigInteger> tokenIDsForEmail = getTokenIDsForEmail(hashedEmail);
        return tokenIDsForEmail.stream().map(this::getCert).collect(Collectors.toList());
    }

    private List<BigInteger> getTokenIDsForEmail(String hashedEmail) {
        try {
            WebSocketService web3jService = new WebSocketService(WEB_SERVICE_ADDRESS, true);
            web3jService.connect();
            Web3j web3j = Web3j.build(web3jService);

            // Load the contract
            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, Credentials.create(SMART_CONTRACT_ADDRESS), new DefaultGasProvider());

            return contract.certificantTokenIds(hashedEmail).send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NFTCertificateDTO getCert(BigInteger index) {

        try {
            WebSocketService web3jService = new WebSocketService(WEB_SERVICE_ADDRESS, true);
            web3jService.connect();
            Web3j web3j = Web3j.build(web3jService);

            Credentials credentials = Credentials.create(SMART_CONTRACT_ADDRESS);

            CertToken contract = CertToken.load(SMART_CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());

            String replace = contract.tokenURI(index)
                    .send()
                    .replace("data:application/json;base64,", "");

            ObjectMapper mapper = new ObjectMapper();
            RawNFTCertificate rawNFTCertificate = mapper.readValue(new String(Base64.getDecoder().decode(replace)), RawNFTCertificate.class);
            return rawNFTCertificate.toNFTCertificateDTO(index);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
