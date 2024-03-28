import { Component,inject, Input,Type } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';
import { NFTCertificate } from '../model/NFTCertificate';
import { CertPipe } from './cert.pipe';
import { BurnConfirmComponent } from '../burn-confirm/burn-confirm.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CertViewerService } from '../services/CertViewerService';

@Component({
  selector: 'certificates-table',
  templateUrl: './certificates-table.component.html',
})
export class CertificatesTableComponent {

  @Input() certificates !: NFTCertificate[];
  private metamaskPrivateKey !: string;

  filter !: string;

 constructor(
   private alertService: AlertService,
   private blockchainService: BlockchainService,
   private certViewerService: CertViewerService,
   private modalService: NgbModal) {}


  async burnTokensForAll(){
    const modalRef = this.modalService.open(BurnConfirmComponent);
    modalRef.result.then(async (result) => {
      if(result)
        for(var cert of this.certificates)
          await this.burnToken(cert.tokenID, result)
    });
  }

  async burnOneToken(tokenID:number){
    const modalRef = this.modalService.open(BurnConfirmComponent);
    modalRef.result.then(async (result) => {
      console.log('result' + result)
      if(result)
        await this.burnToken(tokenID, result)
    });

    this.certViewerService.refreshCache();
  }
  
  async burnToken(tokenID:number,metamaskPrivateKey:string ){
    await this.blockchainService.burn(tokenID, metamaskPrivateKey, this.certViewerService, this.alertService)
  }
}
