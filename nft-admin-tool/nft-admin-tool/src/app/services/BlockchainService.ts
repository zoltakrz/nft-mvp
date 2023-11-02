import { Wallet, ethers } from "ethers";

export class BlockchainService {

  contractABI  = [
    {"type":"function",
    "stateMutability":"nonpayable",
    "outputs":[],
    "name":"safeMint",
    "inputs":[
      {"type":"address","name":"to","internalType":"address"},
      {"type":"string","name":"certificant","internalType":"string"},
      {"type":"string","name":"uri","internalType":"string"}
    ]
    },
    { "type":"function",
      "stateMutability":"nonpayable",
      "outputs":[],
      "name":"burn",
      "inputs":[{
        "type":"uint256","name":"tokenId","internalType":"uint256"}
       ]
    },
    { "type":"function",
      "stateMutability":"view",
      "outputs":[{"type":"uint256[]","name":"","internalType":"uint256[]"}],
      "name":"certificantTokenIds",
      "inputs":[{"type":"string","name":"certificant","internalType":"string"}]
    }
  ];

  provider = new ethers.BrowserProvider((window as any).ethereum);
  nftOwner = String(localStorage.getItem('metamask'));
  smartContractAdress = "0x70C0B60E84BDeeC72E855325521d7D51F105239f";


  async safemint(certificant: string, url: string, walletPrivateKey:string) {

    try {
     let signer = (walletPrivateKey === "") ? await this.provider.getSigner() : new ethers.Wallet(walletPrivateKey, this.provider);
     const contract = new ethers.Contract(this.smartContractAdress, this.contractABI, signer);

     const transaction = await contract['safeMint'](this.nftOwner,certificant,url);
      await transaction.wait();
      console.log('Tokens minted successfully!');
    } catch (error) {
      console.error('Error minting tokens:', error);
    }
  }
  
  async burn(tokenID : number, walletPrivateKey:string) {

    try {
     let signer = (walletPrivateKey === "") ? (await this.provider.getSigner()) : new ethers.Wallet(walletPrivateKey, this.provider);
     const contract = new ethers.Contract(this.smartContractAdress, this.contractABI, signer);

      const transaction = await contract['burn'](tokenID);
      await transaction.wait();
      console.log('Tokens burnt successfully! ' + tokenID.toString());

    } catch (error) {
      console.error('Error burning tokens:', error);
    }
  }

  async burnForUser(hashedEmail : string, walletPrivateKey:string) {
    try {

      let signer = (walletPrivateKey === "") ? (await this.provider.getSigner()) : new ethers.Wallet(walletPrivateKey, this.provider);
      const contract = new ethers.Contract(this.smartContractAdress, this.contractABI, signer);

      let idsPromise = await contract['certificantTokenIds'](hashedEmail);
      let tokenIds : string[] = [];
      await Promise.all([idsPromise]).then((values) => {
        tokenIds = values[0];
      });

      const tokenIdsNum = tokenIds.map(id => Number(id));

      for (var idNum of tokenIdsNum)
      {
        if(idNum != 97){
        const transaction = await contract['burn'](idNum);
        await transaction.wait();
        console.log('Token burnt successfully! ' + idNum.toString());}
      } 

    } catch (error) {
      console.error('Error burning tokens:', error);
    }
  }
}
