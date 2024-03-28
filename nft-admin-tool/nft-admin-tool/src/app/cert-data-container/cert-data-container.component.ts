import { Component,inject, Input,Type } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import { AlertService } from '../_alert';
import { NFTCertificate } from '../model/NFTCertificate';
import { CertificatesTableComponent } from '../certificates-table/certificates-table.component';
import { CertificantsTableComponent } from '../certificants-table/certificants-table.component';

@Component({
  selector: 'cert-data-container',
  templateUrl: './cert-data-container.component.html',
})
export class CertDataContainerComponent {

  @Input() currentCertificates !: NFTCertificate[];
  @Input()lapsedCertificates !: NFTCertificate[];
  @Input() newCertificants !: NFTUser[];

  active = 2;
  filter !: string;

 constructor(
   private alertService: AlertService) {}

}
