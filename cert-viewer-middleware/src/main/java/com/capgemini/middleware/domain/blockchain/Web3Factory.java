package com.capgemini.middleware.domain.blockchain;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

public class Web3Factory {
    private static final String WEB_SERVICE_ADDRESS = "wss://apigateway-webapp.azurewebsites.net";

    public static Web3j getConnectedWeb3j() throws ConnectException {
        WebSocketService web3jService = new WebSocketService(WEB_SERVICE_ADDRESS, true);
        web3jService.connect();
        return Web3j.build(web3jService);
    }
}
