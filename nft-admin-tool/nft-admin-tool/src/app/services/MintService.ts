import { Wallet, ethers } from "ethers";

export class MintService {

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
    }
  ];
  provider = new ethers.BrowserProvider((window as any).ethereum);
  nftOwner = String(localStorage.getItem('metamask'));

  async safemint(certificant: string, url: string, walletPrivateKey:string) {

    try {
     let signer = (walletPrivateKey === "") ? await this.provider.getSigner() : new ethers.Wallet(walletPrivateKey, this.provider);
     const contract = new ethers.Contract("0x70C0B60E84BDeeC72E855325521d7D51F105239f", this.contractABI, signer);

     const transaction = await contract['safeMint'](this.nftOwner,certificant,url);
      await transaction.wait();
      console.log('Tokens minted successfully!');
    } catch (error) {
      console.error('Error minting tokens:', error);
    }
  }
}
