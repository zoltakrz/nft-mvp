import { CertificateDTO } from "./cert-table";

interface CertificateContract {
    firstName: string;
    hashedEmail: string;
    lastName: string;
    certType: string;
    certLevel: string;
    tokenId: string;
}

interface MiddlewareResponse {
    certificates: CertificateContract[];
    updateTimeStamp: string;
}

const mintedTokenURLPrefix = "https://apigateway-webapp.azurewebsites.net/blockscout/token/0x70c0b60e84bdeec72e855325521d7d51f105239f/instance/";

export async function getCertificates(email: string) {
    var middlewareURL = 'https://certviewer-middleware.azurewebsites.net'
    // var middlewareURL = 'http://localhost:8080'

    const response = await fetch(middlewareURL + '/v1/certificates/' + email);
    const middlewareRes: MiddlewareResponse = await response.json();

    let certificates = middlewareRes.certificates.map(certificateContract => {
        let certificate: CertificateDTO = {
            name: certificateContract.firstName,
            surname: certificateContract.lastName,
            certType: certificateContract.certType,
            certGrade: certificateContract.certLevel,
            mintURL: mintedTokenURLPrefix + certificateContract.tokenId,
            certLogoURL: "/" + certificateContract.certLevel + "_" + certificateContract.certType.replace(" ", "_") + ".svg"
        };

        return certificate
    })

    if (certificates.length === 1) {
        console.log("There is " + certificates.length + " certificate for: " + email);
    } else {
        console.log("There are " + certificates.length + " certificates for: " + email);
    }

    return certificates
}
