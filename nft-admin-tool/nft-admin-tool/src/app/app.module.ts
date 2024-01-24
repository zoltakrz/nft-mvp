import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertLoader } from './certLoader/certLoader.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule } from './_alert';
import { CertViewerService } from './services/CertViewerService';
import { CertTable } from './certTable/certTable.component';

@NgModule({
  declarations: [
    AppComponent,
    CertLoader,
    CertTable,
    WalletloginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule ,
    AlertModule
  ],
  providers: [CertViewerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
