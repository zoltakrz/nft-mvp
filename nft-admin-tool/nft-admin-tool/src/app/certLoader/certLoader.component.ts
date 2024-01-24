import { Component, OnInit } from '@angular/core';
import {NFTUser} from "../model/NFTUser";
import {NFTUserService} from "../services/NFTUserService";
import * as XLSX from 'xlsx';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';



@Component({
  selector: 'cert-loader',
   templateUrl: './certloader.component.html',
})

export class CertLoader {

  certificate$: any;
  certTypeVal="";
  nftUsers: NFTUser[]= [];
  nftUserService: NFTUserService = new NFTUserService();
  blockchainService: BlockchainService = new BlockchainService();
  fileContent: any;
  metamaskPrivateKey:string = "";

  constructor(
    private http: HttpClient,
    public alertService: AlertService) {
    }

  onFileChange(evt: any) {
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    this.fileContent = target.files[0];
  }

  load(){
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

  getMetamaskAddress(){
    return String(localStorage.getItem('metamask'));
  }
}
