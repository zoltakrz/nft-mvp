# Cache

Middleware is caching data from blockchain for performance reasons.

It syncs up every hour.


# API

### getCertificatesForEmailOnDemand [GET]

Get all certificates associated with a Capgemini email straight from blockchain
ignoring the cache (for dev purposes):
+ **/v1/onDemand/certificates/{email}**

{email} -> name.forname@capgemini.com

### getCertificatesForEmail [GET]

Get all certificates associated with a Capgemini email:
+ **/v1/certificates/{email}**

{email} -> name.forname@capgemini.com

### getCertificatesForAddressOfOwner [GET]

Get all certificates minted by specific address of wallet:
+ **/v1/certificates/ofOwner/{address}**

{address} -> 0xd034739c2ae807c70cd703092b946f62a49509d1

### getAllCertificates [GET]

Get all certificates in cache:
+ **/v1/certificates**
+ **/v1/certificates?certType={type}**

{type} -> ARCHITECT or ENGAGEMENT_MANAGEMENT

### getAllCertificatesWithoutTestOnes [GET]

Get all certificates in cache (without ones meant for testing):
+ **/v1/certificatesWithoutTestOnes**
+ **/v1/certificatesWithoutTestOnes?certType={type}**

{type} -> ARCHITECT or ENGAGEMENT_MANAGEMENT

### refreshCache [PUT]

Refresh Cache of saved certificates:
+ **/v1/refreshCache**

# Generating Java Wrapper class for smart contract

You can find original smart contract in resources

Firstly you have to process your smart contract using `solc`

`solc CertToken_flattened.sol --bin --abi --optimize -o ./`

Then using Web3j cli

`web3j generate solidity -b ./CertToken.bin -a ./CertToken.abi -o ./ -p com.your.organisation.name`

As for Web3j cli version 1.5 it created java file that didn't compile, although only methods that didn't compile
are unused one so for now I simply deleted them
