package com.capgemini.middleware.infrastructure.adapters.input.rest;

import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.utills.Keccak256Encoder;
import com.capgemini.middleware.infrastructure.contract.ContractCertificates;
import com.capgemini.middleware.ports.input.GetNFTCertificatesUseCase;
import com.capgemini.middleware.domain.model.RawNFTCertificate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class NFTCertificateRestAdapter {

    private final GetNFTCertificatesUseCase getNFTCertificatesUseCase;

    @GetMapping(value = "/certificates/{email}")
    public ResponseEntity<List<ContractCertificates>> getCertificatesForEmail(@PathVariable String email) {
        Collection<NFTCertificateDTO> nftCertificatesForEmail = getNFTCertificatesUseCase.getNFTCertificatesForEmail(email);
        List<ContractCertificates> mappedCertificates = nftCertificatesForEmail.stream()
                .map(ContractCertificates::new)
                .toList();
        return new ResponseEntity<>(mappedCertificates, HttpStatus.OK);
    }
}
