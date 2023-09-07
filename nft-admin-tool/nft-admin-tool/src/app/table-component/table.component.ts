import { Component } from '@angular/core';
import {NFTUser} from "../user-model/NFTUser";
import {NFTUserService} from "../NFTUserService";
import {MintService} from "../MintService";

import * as XLSX from 'xlsx';

import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-table',
   templateUrl: './table.component.html',
})

export class TableJSComponent {

   certTypeVal="";
   nft_users: NFTUser[]= [];
   nftUserService: NFTUserService = new NFTUserService();
   fileContent: any;

  constructor(
    private http: HttpClient) { }

  onFileChange(evt: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    this.fileContent = target.files[0];
  }

  load() {
    if(!this.fileContent) {
      throw new Error('No file is chosen');
    }
    const reader: FileReader = new FileReader();
    reader.onload = (e: any) => {
      /* read workbook */
      const bstr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(bstr, { type: 'binary' });
      /* grab first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];
      const opt: XLSX.Sheet2JSONOpts = { range: 1}

       if(this.nft_users?.length > 0){
              this.nft_users = [];
       }

       console.log(this.nft_users);
      /* save data */
      let json;
      if(this.certTypeVal == 'engagement management') {
        json = XLSX.utils.sheet_to_json(ws , {blankrows: false})
      }
      else {
        json = XLSX.utils.sheet_to_json(ws, opt)
      }
      this.nft_users = this.nftUserService.getNFTUsers(json,this.certTypeVal);

    };
    reader.readAsBinaryString(this.fileContent);
  }

  userSafeMint(hashedEmail	: string, data_json_base64 : string){
      MintService.safemint(hashedEmail,data_json_base64);
  }
}
