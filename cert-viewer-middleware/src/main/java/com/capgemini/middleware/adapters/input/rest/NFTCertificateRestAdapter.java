package com.capgemini.middleware.adapters.input.rest;

import com.capgemini.middleware.adapters.contract.ContractCertificates;
import com.capgemini.middleware.adapters.contract.MiddlewareResponse;
import com.capgemini.middleware.domain.blockchain.CertificateSnapshot;
import com.capgemini.middleware.ports.GetNFTCertificatesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1", produces = "application/json;charset=UTF-8")
@AllArgsConstructor
public class NFTCertificateRestAdapter {

    private final GetNFTCertificatesUseCase getNFTCertificatesUseCase;

    @GetMapping(value = "/certificates/{email}")
    public ResponseEntity<MiddlewareResponse> getCertificatesForEmail(@PathVariable String email) {
        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getNFTCertificatesForEmail(email);

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/certificates/ofOwner/{address}")
    public ResponseEntity<MiddlewareResponse> getCertificatesForAddressOfOwner(@PathVariable String address) {
        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getNFTCertificatesForAddressOfOwner(address);

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/certificates")
    public ResponseEntity<MiddlewareResponse> getAllCertificates() {
        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getAllNFTCertificates();

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .filter(cert -> !cert.getLastName().toLowerCase().contains("fortesting"))
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }
}
