import { Component, inject, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-safe-confirm',
  templateUrl: './safe-confirm.component.html',
})
export class SafeConfirmComponent {

  @Input() metamaskPrivateKey !: string;

  constructor(public modal: NgbActiveModal) { }

}
