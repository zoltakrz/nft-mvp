
import { AbiItem } from 'web3-utils'
import Web3 from "web3";
import Abi from './../certToken.json'
import { keccak256, toUtf8Bytes } from "ethers/lib/utils";
import { CertificateDTO } from "./cert-table";

interface CertificateContract {
    firstName: string;
    hashedEmail: string;
    lastName: string;
    certType: string;
    certLevel: string;
    tokenId: string;
}

const mintedTokenURLPrefix = "https://apigateway-webapp.azurewebsites.net/blockscout/token/0x70c0b60e84bdeec72e855325521d7d51f105239f/instance/";
const webServiceAdress = 'wss://apigateway-webapp.azurewebsites.net';
const smartContractAdress = "0x70C0B60E84BDeeC72E855325521d7D51F105239f";

async function getResult(hashedEmail: string) {
    var options = {
        timeout: 30000,
        // Useful if requests result are large
        clientConfig: {
            maxReceivedFrameSize: 100000000,   // bytes - default: 1MiB
            maxReceivedMessageSize: 100000000, // bytes - default: 8MiB
        },
        // Enable auto reconnection
        reconnect: {
            auto: true,
            delay: 5000, // ms
            maxAttempts: 5,
            onTimeout: false
        }
    };

    var web3 = new Web3(new Web3.providers.WebsocketProvider(webServiceAdress, options));
    const daiToken = new web3.eth.Contract(Abi as AbiItem[], smartContractAdress)
    const certificantTokenIdsFunction = daiToken.methods.certificantTokenIds(hashedEmail)

    let certsIds = await certificantTokenIdsFunction.call()

    var certificates: CertificateContract[] = await Promise.all(certsIds.map(async (id: string): Promise<CertificateContract> => {
        const tokenURIFuntion = daiToken.methods.tokenURI(id)
        var certEncodedWithPrefix: string = await tokenURIFuntion.call()
        var certEncoded: string = certEncodedWithPrefix.replace('data:application/json;base64,', '')
        var cert: string = b64DecodeUnicode(certEncoded)
        let certificateContract: CertificateContract = JSON.parse(cert);
        certificateContract.tokenId = id
        return certificateContract
    }));

    return certificates
}

function b64DecodeUnicode(str: string) {
      return decodeURIComponent(atob(str).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
}

export async function getCertificates(email: string) {
    let hashedEmail = keccak256(toUtf8Bytes(email))

    var certificateContracts: CertificateContract[] = await getResult(hashedEmail)

    let certificates = certificateContracts.map(certificateContract => {
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
