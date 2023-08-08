import { Component } from '@angular/core';

import {NFTUser} from "./NFTUser";
import {Architect} from "./Architect";
import {NFTUserService} from "./NFTUserService"

import * as XLSX from 'xlsx';

@Component({
  selector: 'app-table',
   templateUrl: './table.component.html',
})

export class TableJSComponent {

   architects: Architect[] = [];
   nft_users: NFTUser[]= [];
   nftUserService: NFTUserService = new NFTUserService();

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
      /* save data */
      this.architects = XLSX.utils.sheet_to_json(ws);

      if(this.nft_users.length>0){
        this.nft_users = [];
      }
      for(let arch of this.architects){
        this.nft_users.push(this.nftUserService.getNftUserFromArchitect(arch));
      }
    };

    reader.readAsBinaryString(target.files[0]);
  }
}
