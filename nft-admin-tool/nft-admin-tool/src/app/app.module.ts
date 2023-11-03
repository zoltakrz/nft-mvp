import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TableJSComponent } from './table-component/table.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule } from './_alert';

@NgModule({
  declarations: [
    AppComponent,
    TableJSComponent,
    WalletloginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    AlertModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
