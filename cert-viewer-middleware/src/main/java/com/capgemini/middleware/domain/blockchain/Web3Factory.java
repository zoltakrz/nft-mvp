package com.capgemini.middleware.domain.blockchain;

import com.capgemini.middleware.domain.blockchain.generated.CertToken;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.net.ConnectException;

public class Web3Factory {
    private static final String WEB_SERVICE_ADDRESS = "wss://apigateway-webapp.azurewebsites.net";
    private static final String SMART_CONTRACT_ADDRESS = "0x70C0B60E84BDeeC72E855325521d7D51F105239f";

    public static Web3j getConnectedWeb3j() throws ConnectException {
        WebSocketService web3jService = new WebSocketService(WEB_SERVICE_ADDRESS, true);
        web3jService.connect();
        return Web3j.build(web3jService);
    }

    public static CertToken getSmartContract(Web3j web3j) {
        return CertToken.load(SMART_CONTRACT_ADDRESS, web3j, Credentials.create(SMART_CONTRACT_ADDRESS), new DefaultGasProvider());
    }
}
