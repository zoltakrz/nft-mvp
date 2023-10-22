import { Wallet, ethers } from "ethers";

export class BurnService {

  contractABI  = [
    { "type":"function",
      "stateMutability":"nonpayable",
      "outputs":[],
      "name":"burn",
      "inputs":[{
        "type":"uint256","name":"tokenId","internalType":"uint256"}
       ]
    }
  ];

  provider = new ethers.BrowserProvider((window as any).ethereum);
  nftOwner = String(localStorage.getItem('metamask'));

  async burn(tokenID : number, walletPrivateKey:string) {

    try {
     let signer = (walletPrivateKey === "") ? await this.provider.getSigner() : new ethers.Wallet(walletPrivateKey, this.provider);
     const contract = new ethers.Contract("0x70C0B60E84BDeeC72E855325521d7D51F105239f", this.contractABI, signer);

     const transaction = await contract['burn'](tokenID);
      await transaction.wait();
      console.log('Tokens burnt successfully!');
    } catch (error) {
      console.error('Error burning tokens:', error);
    }
  }
}
