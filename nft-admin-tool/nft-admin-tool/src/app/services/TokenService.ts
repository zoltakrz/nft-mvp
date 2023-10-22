import { Wallet, ethers } from "ethers";

export class BurnService {

  contractABI  = [
    {
    }
  ];

  provider = new ethers.BrowserProvider((window as any).ethereum);
  nftOwner = String(localStorage.getItem('metamask'));

  async burnNFT(tokenID : number, walletPrivateKey:string) {

    let signer = (walletPrivateKey === "") ? await this.provider.getSigner() : new ethers.Wallet(walletPrivateKey, this.provider);
    const contract = new ethers.Contract("0x70C0B60E84BDeeC72E855325521d7D51F105239f", this.contractABI, signer);

    try {
     const transaction = await contract['burn'](tokenID);
      await transaction.wait();
      console.log('Tokens burnt successfully!');
    } catch (error) {
      console.error('Error burning tokens:', error);
    }
  }
}
