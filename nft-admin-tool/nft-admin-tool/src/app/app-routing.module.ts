import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CertLoader } from './certLoader/certLoader.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';

const routes: Routes = [

  {
    path: '',
    component: WalletloginComponent,
  },
  {
    path: 'certloader',
    component: CertLoader,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
