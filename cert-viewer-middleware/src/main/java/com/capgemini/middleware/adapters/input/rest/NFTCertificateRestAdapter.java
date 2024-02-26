package com.capgemini.middleware.adapters.input.rest;

import com.capgemini.middleware.adapters.contract.ContractCertificates;
import com.capgemini.middleware.adapters.contract.MiddlewareResponse;
import com.capgemini.middleware.adapters.exception.InvalidCertTypeException;
import com.capgemini.middleware.domain.blockchain.CertificateSnapshot;
import com.capgemini.middleware.domain.model.CertType;
import com.capgemini.middleware.domain.model.NFTCertificateDTO;
import com.capgemini.middleware.domain.utills.Keccak256Encoder;
import com.capgemini.middleware.ports.GetNFTCertificatesUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json;charset=UTF-8")
@AllArgsConstructor
public class NFTCertificateRestAdapter {

    private final GetNFTCertificatesUseCase getNFTCertificatesUseCase;

    @GetMapping(value = "/onDemand/certificates/{email}")
    public ResponseEntity<MiddlewareResponse> getCertificatesForEmailOnDemand(@PathVariable String email) {
        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getNFTCertificatesForEmail(email);

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/certificates/{email}")
    public ResponseEntity<MiddlewareResponse> getCertificatesForEmail(@PathVariable String email) {
        final Predicate<NFTCertificateDTO> filterForCertsWithEmail = cert ->
                cert.getHashedEmail().equals(Keccak256Encoder.encode(email.toLowerCase()));

        return getAllCerts(filterForCertsWithEmail, Optional.empty());
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
    public ResponseEntity<MiddlewareResponse> getAllCertificates(@RequestParam(required = false) String certType) {
        final Predicate<NFTCertificateDTO> filterOutNothing = cert -> true;
        return getAllCerts(filterOutNothing, Optional.ofNullable(certType));
    }

    @GetMapping(value = "/certificatesWithoutTestOnes")
    public ResponseEntity<MiddlewareResponse> getAllCertificatesWithoutTestOnes(@RequestParam(required = false) String certType) {
        final Predicate<NFTCertificateDTO> filterOutTestCerts = cert ->
                !cert.getLastName().toLowerCase().contains("fortesting");
        return getAllCerts(filterOutTestCerts, Optional.ofNullable(certType));
    }

    @PutMapping(value = "refreshCache")
    public ResponseEntity<String> refreshCache() {
        getNFTCertificatesUseCase.updateCache();
        return ResponseEntity.ok("updated");
    }

    @NotNull
    private ResponseEntity<MiddlewareResponse> getAllCerts(Predicate<NFTCertificateDTO> filterOutTestCerts, Optional<String> certType) {
        final Predicate<NFTCertificateDTO> filterByCertType = getCertTypeFiltering(certType);

        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getAllNFTCertificates();

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .filter(filterOutTestCerts)
                .filter(filterByCertType)
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());

        log.info("Returning {} certificates", middlewareResponse.certificates().size());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }

    private Predicate<NFTCertificateDTO> getCertTypeFiltering(Optional<String> certType) {
        if (certType.isEmpty()) {
            return cert -> true;
        }
        try {
            CertType certTypeEnum = CertType.valueOf(certType.get());
            return cert -> cert.getCertType() == certTypeEnum;
        } catch(Exception e) {
            throw new InvalidCertTypeException(e, String.format("Value: {%s} couldn't be mapped to CertType enum", certType.get()));
        }
    }
}
