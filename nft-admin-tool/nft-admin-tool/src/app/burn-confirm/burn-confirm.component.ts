import { Component, inject, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-burn-confirm',
  templateUrl: './burn-confirm.component.html',
})
export class BurnConfirmComponent {

  @Input() metamaskPrivateKey !: string;

  constructor(public modal: NgbActiveModal) { }

}
