import { Component, OnInit } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import {NFTUserService} from "../services/NFTUserService";
import * as XLSX from 'xlsx';
import { AlertService } from '../_alert';
import { CertViewerService } from '../services/CertViewerService';
import { NFTCertificate } from '../model/NFTCertificate';

@Component({
  selector: 'cert-loader',
   templateUrl: './certloader.component.html',
})

export class CertLoader {
  certTypeVal="";
  fileContent: any;
  metamaskAddress:string = String(localStorage.getItem('metamask'));
  metamaskPrivateKey:string="";

  allCertificates !: NFTCertificate[]
  nftUsers !: NFTUser[];

  currentCertificates !: NFTCertificate[];
  lapsedCertificates !: NFTCertificate[];
  newCertificants !: NFTUser[];
  
  constructor(
    private alertService: AlertService,
    private nftUserService: NFTUserService,
    private certViewerService: CertViewerService) {
    }

  onFileChange(evt: any) {
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    this.fileContent = target.files[0];
  }

  load(){
    this.setUsersFromFile();
    this.setAllCertificates();
  }

  setUsersFromFile():void {
    if(!this.fileContent)
        throw new Error('No file is chosen');

        const reader: FileReader = new FileReader();
        reader.onload = (e: any) => {
          const bstr: string = e.target.result;
          const wb: XLSX.WorkBook = XLSX.read(bstr, { type: 'binary' });
          const opt: XLSX.Sheet2JSONOpts = { range: 1}

          if(this.nftUsers?.length > 0)
            this.nftUsers = [];

          let json;
          if(this.certTypeVal == 'engagement management') {
            json = XLSX.utils.sheet_to_json(wb.Sheets['FS EM Certified'] , {blankrows: false})
          }
          else {
            json = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]], opt)
          }
          this.nftUsers = this.nftUserService.getNFTUsers(json,this.certTypeVal);
        };
        reader.readAsBinaryString(this.fileContent);
  }

  private setAllCertificates(){
    this.certViewerService.getAllNFTCertificates().pipe().subscribe(data => {
      
      this.allCertificates = data;
            
      this.currentCertificates = this.allCertificates.filter(cert => {
        return (cert.certType === 'Architect' && this.nftUsers.some(user => 
          (user.firstName===cert.firstName && 
            user.lastName===cert.lastName &&
            user.certLevel === cert.certLevel)))
      })

      
      this.lapsedCertificates = this.allCertificates.filter(cert => {
        return (cert.certType === 'Architect' && !this.nftUsers.some(user => 
          (user.firstName===cert.firstName && 
            user.lastName===cert.lastName &&
            user.certLevel === cert.certLevel)))
      })


      this.newCertificants = this.nftUsers.filter(user => {
        return !this.currentCertificates.some(cert => (
          user.firstName===cert.firstName && 
            user.lastName===cert.lastName &&
            user.certLevel === cert.certLevel
        ))
      })
        
      console.log(this.allCertificates.length)
      console.log(this.currentCertificates.length)
      console.log(this.lapsedCertificates.length)
      console.log(this.newCertificants.length)
    });
  }
}
