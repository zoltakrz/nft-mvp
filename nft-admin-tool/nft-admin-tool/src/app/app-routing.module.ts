import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TableJSComponent } from './table-component/table.component';
import { WalletloginComponent } from './walletlogin-component/walletlogin.component';

const routes: Routes = [

  {
    path: '',
    component: WalletloginComponent,
  },
  {
    path: 'data',
    component: TableJSComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
