import { Component,inject, Input,Type } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';
import { NFTCertificate } from '../model/NFTCertificate';
import { CertPipe } from './cert.pipe';
import { UserPipe } from './user.pipe';
import { BurnConfirmComponent } from '../burn-confirm/burn-confirm.component';



import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'cert-table',
  templateUrl: './certTable.component.html',
})

export class CertTable {

   @Input() currentCertificates !: NFTCertificate[];
   @Input()lapsedCertificates !: NFTCertificate[];
   @Input() newCertificants !: NFTUser[];

   active = 2;
   filter !: string;

  constructor(
    private alertService: AlertService,
    private blockchainService: BlockchainService,
    private modalService: NgbModal) {}


  async safeMintForUser(hashedEmail	: string, data_json_base64 : string){
    //await this.blockchainService.safemint(hashedEmail,data_json_base64,this.metamaskPrivateKey, this.alertService);
  }
  async safeMintForAll(){
    // for (var nftUser of this.newCertificants)
    //   await this.safeMintForUser(nftUser.hashedEmail,nftUser.data_json_base64)
  }

  async burnTokensForAll(){
    let confirmModal = this.confirmBurn();
    if (confirmModal.burnConfirm){
      for(var cert of this.lapsedCertificates)
        await this.burnToken(cert.tokenID, confirmModal.privateKey)
    }
  }

  async burnOneToken(tokenID:number){
    let confirmModal = this.confirmBurn();
    if (confirmModal.burnConfirm){
        await this.burnToken(tokenID, confirmModal.privateKey)
    }
  }
  
  async burnToken(tokenID:number,metamaskPrivateKey:string ){
    await this.blockchainService.burn(tokenID, metamaskPrivateKey, this.alertService)
  }


  confirmBurn(){
    const modalRef = this.modalService.open(BurnConfirmComponent);

    modalRef.result.then((result) => {
      return {burnConfirm : true, privateKey: result};
    });

    return {burnConfirm : false, privateKey: ''}
  }
}

