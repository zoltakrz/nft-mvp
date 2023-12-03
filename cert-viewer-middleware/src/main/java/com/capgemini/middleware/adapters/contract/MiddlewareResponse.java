package com.capgemini.middleware.adapters.contract;

import java.util.List;

public record MiddlewareResponse (List<ContractCertificates> certificates, String updateTimeStamp){
}
