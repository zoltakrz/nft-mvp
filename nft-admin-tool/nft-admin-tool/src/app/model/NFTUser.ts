import { TitleCasePipe } from '@angular/common';
import { ethers } from "ethers";

export class NFTUser {

        public email: string = "";
        public hashedEmail	: string= "";
        public firstName	: string= "";
        public lastName	: string= "";
        public certType	: string= "";
        public certLevel	: string= "";
        public data_json_string	: string= "";
        public data_json_base64 : string= "";
        public dataExpired: string = "";
        public tokenIds: number[] = [];

      constructor() {};

      setEmail(email:string){
        this.email = email ? email.toLowerCase():'';

      }

      setHashedEmail(email:string){
        if(email) 
          this.hashedEmail = ethers.id(email.toLowerCase());
      }

      setFirstName(fName:string){
        this.firstName = fName;
      };
      setLastName(lName:string){
        this.lastName = lName;
      };

      setFirstAndLastNameFromEmail(email:string){
        if(!email) {
          return;
        }
        let name_and_last_name = (email.split('@')[0]).split('.');
        let names_count = name_and_last_name.length;

        let titlecasePipe = new TitleCasePipe();

        this.firstName = titlecasePipe.transform(name_and_last_name[0].replace('-',' '));
        this.lastName = titlecasePipe.transform(name_and_last_name[names_count-1].replace('-',' '));
      }


      setCertType(cert_type:string){
        this.certType = cert_type;
      }
      setCertLevel(cert_level:string){
        this.certLevel = cert_level;
      }

      setJsonStringForNFTUser(){
        this.data_json_string = this.buildJsonStringForNFTUser(this.hashedEmail, this.firstName, this.lastName, this.certType, this.certLevel);
      }

      buildJsonStringForNFTUser(hash:string, fName:string, lName:string, cType:string, cLevel:string):string{

        let data_json_object = {
          hashedEmail:hash,
          firstName: fName,
          lastName:lName,
          certType:cType,
          certLevel:cLevel
        };

        return JSON.stringify(data_json_object);
      }

      setDataJsonBase64ForNFTUser() {
        this.data_json_base64 = 'data:application/json;base64,' +   ethers.encodeBase64(ethers.toUtf8Bytes(this.data_json_string));

      }

      setTokenIds(tokenIds:number[]){
        this.tokenIds = tokenIds;
      }
  }
