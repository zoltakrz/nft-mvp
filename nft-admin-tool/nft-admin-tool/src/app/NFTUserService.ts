import { Component } from '@angular/core';
import {NFTUser} from "./user-model/NFTUser";
import {Architect} from "./user-model/Architect";
import {EM} from "./user-model/EM";

export class NFTUserService {

     constructor() {};

     getNFTUsers(json : any, certType:string):NFTUser[] {

             let nftusers: NFTUser[] = [];

             if (certType == "architect"){
              let architects : Architect[] = json;
              for(let arch of architects){
                nftusers.push(this.getNftUserFromArch(arch,certType));
              }
             }
             else if (certType == "em"){
             let ems : EM[] = json;
              for(let em of ems){
                nftusers.push(this.getNftUserFromEM(em,certType));
              }
             }
            return nftusers;
     }

     getNftUserFromArch(architect:Architect, certType:string):NFTUser {

               let nftUser = new NFTUser();

               nftUser.setEmail(architect.Name);
               nftUser.setHashedEmail(architect.Name);
               nftUser.setFirstAndLastNameFromEmail(architect.Name);
               nftUser.setCertType(certType);
               nftUser.setCertLevel(String(architect.Certification_Level));
               nftUser.setJsonStringForNFTUser();
               nftUser.setDataJsonBase64ForNFTUser();

               return nftUser;
     }

     getNftUserFromEM(em:EM, certType:string):NFTUser {

               let nftUser = new NFTUser();

               nftUser.setEmail(em.Email);
               nftUser.setHashedEmail(em.Email);
               nftUser.setFirstName(em.First_Name);
               nftUser.setLastName(em.Last_Name);
               nftUser.setCertType(certType);
               nftUser.setCertLevel(String(em.Current_Certification_Level));
               nftUser.setJsonStringForNFTUser();
               nftUser.setDataJsonBase64ForNFTUser();

               return nftUser;
     }
}







