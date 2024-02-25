import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbNavModule, NgbDropdownModule, NgbHighlight,NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertLoader } from './certLoader/certLoader.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule } from './_alert';
import { CertViewerService } from './services/CertViewerService';
import { CertTable } from './certTable/certTable.component';
import { CertPipe } from './certTable/cert.pipe';
import { UserPipe } from './certTable/user.pipe';
import { BurnConfirmComponent } from './burn-confirm/burn-confirm.component';


@NgModule({
  declarations: [
    AppComponent,
    CertLoader,
    CertTable,
    WalletloginComponent,
    CertPipe,
    UserPipe,
    BurnConfirmComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule ,
    AlertModule,
    NgbNavModule,
    NgbDropdownModule,
    NgbHighlight,
  ],

  providers: [CertViewerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
