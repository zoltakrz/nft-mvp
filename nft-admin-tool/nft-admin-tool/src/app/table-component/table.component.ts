import { Component } from '@angular/core';
import {NFTUser} from "../user-model/NFTUser";
import {NFTUserService} from "../services/NFTUserService";
import * as XLSX from 'xlsx';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AlertService } from '../_alert';
import { BlockchainService } from '../services/BlockchainService';

@Component({
  selector: 'app-table',
   templateUrl: './table.component.html',
})

export class TableJSComponent {

   certTypeVal="";
   nftUsers: NFTUser[]= [];
   nftUserService: NFTUserService = new NFTUserService();
   blockchainService: BlockchainService = new BlockchainService();
   fileContent: any;
   metamaskPrivateKey:string = "";

  constructor(
    private http: HttpClient,
    public alertService: AlertService) { }

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

  getMetamaskAddress(){
    return String(localStorage.getItem('metamask'));
  }
}
