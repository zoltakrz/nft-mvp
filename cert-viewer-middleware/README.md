## Generating Java Wrapper class for smart contract

You can find original smart contract in resources

Firstly you have to process your smart contract using `solc`

`solc CertToken_flattened.sol --bin --abi --optimize -o ./`

Then using Web3j cli

`web3j generate solidity -b ./CertToken.bin -a ./CertToken.abi -o ./ -p com.your.organisation.name`

As for Web3j cli version 1.5 it created java file that didn't compile, although only methods that didn't compile
are unused one so for now I simply deleted them
