import { Component } from '@angular/core';
import { TitleCasePipe } from '@angular/common';

import * as XLSX from 'xlsx';
import { ethers } from "ethers";

interface NFTUser {
  email: string;
  hashedEmail	: string;
  firstName	: string;
  lastName	: string;
  certType	: string;
  certLevel	: string;
  data_json_string	: string;
  data_json_base64 : string;
}

interface Architect {
  Name: string;
  Certification_Level	: string;
  Global_Grade	: string;
  SBU_GBL	: string;
  Region	: string;
  Architect_Base_Country	: string;
  Status  : string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'nft-admin-tool';
  architectsJsonFromExcel:any;
  nft_users: NFTUser[] = [];
  architects: Architect[] = []

  ReadExcel(event : any)
  {
    let file = event.target.files[0];
    let fileReader = new FileReader();
    fileReader.readAsBinaryString(file);
    fileReader.onload = (e)=>{
      let workBook = XLSX.read(fileReader.result,{type:'binary'})
      let sheetNames = workBook.SheetNames;
      this.architectsJsonFromExcel = XLSX.utils.sheet_to_json(workBook.Sheets[sheetNames[0]]);
      this.architects = this.architectsJsonFromExcel;

      for(let arch of this.architects){
        this.nft_users.push(this.getNftUser(arch));
      }
    }
  }

  getNftUser(architect:Architect):NFTUser {

        let hash = ethers.id(architect.Name.toString());

        let name_and_last_name = (architect.Name.split('@')[0]).split('.');
        let names_count = name_and_last_name.length;

        let titlecasePipe = new TitleCasePipe();
        let name = titlecasePipe.transform(name_and_last_name[0].replace('-',' '));
        let lastName = titlecasePipe.transform(name_and_last_name[names_count-1]);

        let data_json_object = {
          hashedEmail:hash,
          firstName: name,
          lastName:lastName,
          certType:"architect",
          certLevel: String(architect.Certification_Level)
        };


        let data_json_string	= JSON.stringify(data_json_object);
        let data_json_base64 = btoa(data_json_string);

        return {
          email: architect.Name,
          hashedEmail	: hash,
          firstName	: name,
          lastName	: lastName,
          certType	: 'architect',
          certLevel	: architect.Certification_Level,
          data_json_string	: data_json_string,
          data_json_base64 : 'data:application/json;base64,' + data_json_base64
        } ;
  }
}
