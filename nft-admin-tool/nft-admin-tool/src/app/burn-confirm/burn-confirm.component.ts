import { Component, inject, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-burn-confirm',
  templateUrl: './burn-confirm.component.html',
  styleUrl: './burn-confirm.component.css'
})
export class BurnConfirmComponent {

  @Input() metamaskPrivateKey !: string;

  constructor(public modal: NgbActiveModal) { }

  burnConfirm(){
    this.modal.close(this.metamaskPrivateKey);
  }

}
