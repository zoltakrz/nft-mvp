import { ethers } from "ethers";

export class MintService {

  static contractABI  = [
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
  ]
  static METAMASK_KEY: string = 'metamask';

  static async safemint(certificant: string, url: string) {

    let provider = new ethers.BrowserProvider((window as any).ethereum)
    let signer = await provider.getSigner();

    const contract = new ethers.Contract("0x70C0B60E84BDeeC72E855325521d7D51F105239f", this.contractABI, signer);
    let nftOwner = String(localStorage.getItem(this.METAMASK_KEY));

    try {
      const transaction = await contract['safeMint'](nftOwner,certificant,url);
      await transaction.wait();
      console.log('Tokens minted successfully!');
    } catch (error) {
      console.error('Error minting tokens:', error);
    }
  }
}
