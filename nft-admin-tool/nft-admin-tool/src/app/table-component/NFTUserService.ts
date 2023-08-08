import { Component } from '@angular/core';
import {NFTUser} from "./NFTUser";
import {Architect} from "./Architect";

export class NFTUserService {

     constructor() {};
     getNftUserFromArchitect(architect:Architect):NFTUser {

               let nftUser = new NFTUser();
               nftUser.setEmail(architect.Name);
               nftUser.setHashedEmail(architect.Name);
               nftUser.setFirstAndLastName(architect.Name);

               nftUser.setCertType("architect");
               nftUser.setCertLevel(String(architect.Certification_Level));

               nftUser.setJsonStringForNFTUser();
               nftUser.setDataJsonBase64ForNFTUser();

               return nftUser;
     }
}







