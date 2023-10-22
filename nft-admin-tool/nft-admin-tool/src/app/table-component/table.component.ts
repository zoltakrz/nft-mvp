import { Component } from '@angular/core';
import {NFTUser} from "../user-model/NFTUser";
import {MintService} from "../services/MintService";
import {BurnService} from "../services/BurnService";
import {NFTUserService} from "../services/NFTUserService";

import * as XLSX from 'xlsx';

import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-table',
   templateUrl: './table.component.html',
})

export class TableJSComponent {

   certTypeVal="";
   nftUsers: NFTUser[]= [];
   nftUserService: NFTUserService = new NFTUserService();
   mintService: MintService = new MintService();
   burnService: BurnService = new BurnService();
   fileContent: any;
   metamaskPrivateKey:string = "";

  constructor(
    private http: HttpClient) { }

  submit(){
    this.load();
  }

  onFileChange(evt: any) {
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    this.fileContent = target.files[0];
  }

  load() : void {
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

  safeMintForUser(hashedEmail	: string, data_json_base64 : string){
      this.mintService.safemint(hashedEmail,data_json_base64,this.metamaskPrivateKey);
  }

  safeMintForAll(){
    for (var nftUser of this.nftUsers){
      this.safeMintForUser(nftUser.hashedEmail,nftUser.data_json_base64)
    }
  }

  burnTokensForUser(tokenIds: number[]){
    for(var id of tokenIds){
        this.burnService.burn(id,this.metamaskPrivateKey);
    }
  }
  burnTokensForAll(){
      for(var nftUser of this.nftUsers){
          this.burnTokensForUser(nftUser.tokenIds)
      }
  }

  getMetamaskAddress(){
    return String(localStorage.getItem('metamask'));
  }
}
