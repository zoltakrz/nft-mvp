import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-walletlogin-component',
  templateUrl: './walletlogin.component.html',
  styleUrls: ['./walletlogin.component.css']
})
export class WalletloginComponent implements OnInit {

  readonly METAMASK_KEY: string = 'metamask';

  public isIdentified: boolean = false;
  public isConnected: boolean = false;
  public ownerAddress: string = '';
  constructor() {}
  ngOnInit() {
    if (this.checkIfMetamaskInstalled()) {
      this.isIdentified = true;

      if (this.checkIfMetamaskConnected()) {
        this.connected();
      }
    }
  }
  checkIfMetamaskInstalled(): boolean {
    if (typeof (window as any).ethereum !== 'undefined') {
      return true;
    }
    return false;
  }

  checkIfMetamaskConnected(): boolean {
    if (localStorage.getItem(this.METAMASK_KEY)) {
      return true;
    }
    return false;
  }
  getMetamaskAdress(): String {
    return String(localStorage.getItem(this.METAMASK_KEY));
  }

  storeMetamask() {
    localStorage.setItem(this.METAMASK_KEY, this.ownerAddress);
  }

  connected() {
    this.isConnected = true;
  }
  disconnected() {
    this.isConnected = false;
  }


  async connectMetamask() {
    const accounts = await (window as any).ethereum.request({
      method: 'eth_requestAccounts',
    });
    const account = accounts[0];
    this.ownerAddress = account;
    this.storeMetamask();
    this.connected();
  }

  disconnectMetaMask() {
    localStorage.setItem(this.METAMASK_KEY,'');
    this.disconnected();
  }

}
