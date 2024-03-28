import { Component,inject, Input,Type } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';
import { UserPipe } from './user.pipe';
import { BurnConfirmComponent } from '../burn-confirm/burn-confirm.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CertificatesTableComponent } from '../certificates-table/certificates-table.component';
import { SafeConfirmComponent } from '../safe-confirm/safe-confirm.component';
import { CertViewerService } from '../services/CertViewerService';

@Component({
  selector: 'certificants-table',
  templateUrl: './certificants-table.component.html',
})
export class CertificantsTableComponent {

  @Input() certificants !: NFTUser[];
  filter !: string;

 constructor(
   private alertService: AlertService,
   private blockchainService: BlockchainService,
   private certViewerService: CertViewerService,
   private modalService: NgbModal) {}

  async safeMintForUser(hashedEmail	: string, data_json_base64 : string, metamaskPrivateKey:string ){
    await this.blockchainService.safemint(hashedEmail, data_json_base64, metamaskPrivateKey, this.certViewerService, this.alertService);
  }

  async safeMintOneCert(hashedEmail	: string, data_json_base64:string){
    const modalRef = this.modalService.open(SafeConfirmComponent);
    modalRef.result.then(async (result) => {
      if(result)
        await this.safeMintForUser(hashedEmail, data_json_base64, result);
        this.certViewerService.refreshCache().pipe().subscribe();
    });

  }

  async safeMintAllCerts(){
    const modalRef = this.modalService.open(SafeConfirmComponent);
    modalRef.result.then(async (result) => {
      if(result)
        for (var nftUser of this.certificants)
          await this.safeMintForUser(nftUser.hashedEmail, nftUser.data_json_base64, result);
        
        this.certViewerService.refreshCache().pipe().subscribe();
    });
  }

}
