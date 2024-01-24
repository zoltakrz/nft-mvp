import { Component, Input, OnInit } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import {NFTUserService} from "../services/NFTUserService";
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';
import { NFTCertificate } from '../model/NFTCertificate';
import { CertViewerService } from '../services/CertViewerService';

@Component({
  selector: 'cert-table',
  templateUrl: './certTable.component.html',
})

export class CertTable {

   allCertificates !: NFTCertificate[];
   active = 1;


   nftUsers: NFTUser[]= [];
   nftUserService: NFTUserService = new NFTUserService();
   blockchainService: BlockchainService = new BlockchainService();
   metamaskPrivateKey:string = "";

  constructor(
    public alertService: AlertService,
    private certViewerService: CertViewerService) {}

  ngOnInit(){
    this.setAllCertificates();
  }

  private setAllCertificates(){
    this.certViewerService.getAllNFTCertificates().pipe().subscribe(data => {
      this.allCertificates = data;
    })
  }

  async safeMintForUser(hashedEmail	: string, data_json_base64 : string){
      await this.blockchainService.safemint(hashedEmail,data_json_base64,this.metamaskPrivateKey, this.alertService);
  }
  async safeMintForAll(){
    for (var nftUser of this.nftUsers){
      await this.safeMintForUser(nftUser.hashedEmail,nftUser.data_json_base64)
    }
  }
  async burnTokensForUser(hashedEmail:string){
    await this.blockchainService.burnForUser(hashedEmail,this.metamaskPrivateKey,this.alertService);
  }
  async burnTokensForAll(){
      for(var nftUser of this.nftUsers)
         await this.burnTokensForUser(nftUser.hashedEmail)  
  }

}
