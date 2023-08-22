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

  constructor(
    private http: HttpClient) { }

  onFileChange(evt: any) {
    /* wire up file reader */
    const target: DataTransfer = <DataTransfer>(evt.target);
    if (target.files.length !== 1) throw new Error('Cannot use multiple files');
    const reader: FileReader = new FileReader();
    reader.onload = (e: any) => {
      /* read workbook */
      const bstr: string = e.target.result;
      const wb: XLSX.WorkBook = XLSX.read(bstr, { type: 'binary' });
      /* grab first sheet */
      const wsname: string = wb.SheetNames[0];
      const ws: XLSX.WorkSheet = wb.Sheets[wsname];

       if(this.nft_users.length>0){
              this.nft_users = [];
       }

       console.log(this.nft_users);
      /* save data */
      this.nft_users = this.nftUserService.getNFTUsers(XLSX.utils.sheet_to_json(ws),this.certTypeVal);

    };
    reader.readAsBinaryString(target.files[0]);
  }

  userSafeMint(hashedEmail	: string, data_json_base64 : string){
      MintService.safemint(hashedEmail,data_json_base64);
  }
}
