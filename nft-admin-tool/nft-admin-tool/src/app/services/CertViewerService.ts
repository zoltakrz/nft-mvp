import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {map,timeout} from "rxjs/operators"
import { NFTCertificate } from "../model/NFTCertificate";
import { Injectable } from '@angular/core';

export interface CertificateResponse {
    certificates : any,
    updateTimeStamp : any
}

@Injectable({
    providedIn: "root"
})
export class CertViewerService {

    //private apiUrl = "http://certviewer-middleware.azurewebsites.net/v1";

    constructor(protected http: HttpClient){}

    getAllNFTCertificates() : Observable<NFTCertificate[]> {
        return  this.http.get<CertificateResponse>('/v1/certificates').pipe(
              timeout(10000),
              map(response => response.certificates)
        );
    }



}
