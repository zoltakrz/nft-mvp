import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbNavModule, NgbDropdownModule, NgbHighlight,NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertLoader } from './cert-loader/certLoader.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule } from './_alert';
import { CertViewerService } from './services/CertViewerService';
import { CertPipe } from './certificates-table/cert.pipe';
import { UserPipe } from './certificants-table/user.pipe';
import { BurnConfirmComponent } from './burn-confirm/burn-confirm.component';
import { SafeConfirmComponent } from './safe-confirm/safe-confirm.component';
import { CertificatesTableComponent } from './certificates-table/certificates-table.component';
import { CertificantsTableComponent } from './certificants-table/certificants-table.component';
import { CertDataContainerComponent } from './cert-data-container/cert-data-container.component';


@NgModule({
  declarations: [
    AppComponent,
    CertLoader,
    CertDataContainerComponent,
    CertificatesTableComponent,
    CertificantsTableComponent,
    WalletloginComponent,
    CertPipe,
    UserPipe,
    BurnConfirmComponent,
    SafeConfirmComponent
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
