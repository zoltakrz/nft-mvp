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
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping(value = "/v1", produces = "application/json;charset=UTF-8")
@AllArgsConstructor
@CrossOrigin(
        origins = {"https://certviewer.azurewebsites.net/", "https://nftadmintool.azurewebsites.net/", "http://localhost:3000", "http://localhost:4200"},
        allowedHeaders = "Requestor-Type",
        exposedHeaders = "X-Get-Header",
        methods = {RequestMethod.GET, RequestMethod.PUT}
)
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

        return getAllCerts(List.of(filterForCertsWithEmail));
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
        return getAllCerts(List.of(getCertTypeFiltering(certType)));
    }

    @GetMapping(value = "/certificatesWithoutTestOnes")
    public ResponseEntity<MiddlewareResponse> getAllCertificatesWithoutTestOnes(@RequestParam(required = false) String certType) {
        final Predicate<NFTCertificateDTO> filterOutTestCerts = cert ->
                !cert.getHashedEmail().toLowerCase().equals(Keccak256Encoder.encode("joe.fortesting@capgemini.com"));
        return getAllCerts(List.of(filterOutTestCerts, getCertTypeFiltering(certType)));
    }

    @PutMapping(value = "refreshCache")
    public ResponseEntity<String> refreshCache() {
        getNFTCertificatesUseCase.updateCache();
        return ResponseEntity.ok("updated");
    }

    @NotNull
    private ResponseEntity<MiddlewareResponse> getAllCerts(List<Predicate<NFTCertificateDTO>> predicates) {
        final CertificateSnapshot certificateSnapshot = getNFTCertificatesUseCase.getAllNFTCertificates();

        final List<ContractCertificates> mappedCertificates = certificateSnapshot.certificates().stream()
                .filter(predicates.stream().reduce(x->true, Predicate::and))
                .map(ContractCertificates::new)
                .toList();

        final var middlewareResponse = new MiddlewareResponse(mappedCertificates, certificateSnapshot.lastUpdateTimestamp());

        log.info("Returning {} certificates", middlewareResponse.certificates().size());
        return new ResponseEntity<>(middlewareResponse, HttpStatus.OK);
    }

    private Predicate<NFTCertificateDTO> getCertTypeFiltering(String certType) {
        if (certType == null || certType.isBlank()) {
            return cert -> true;
        }
        try {
            CertType certTypeEnum = CertType.valueOf(certType);
            return cert -> cert.getCertType() == certTypeEnum;
        } catch(Exception e) {
            throw new InvalidCertTypeException(e, String.format("Value: {%s} couldn't be mapped to CertType enum", certType));
        }
    }
}
