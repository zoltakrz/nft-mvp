import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BurnConfirmComponent } from './burn-confirm.component';

describe('BurnConfirmComponent', () => {
  let component: BurnConfirmComponent;
  let fixture: ComponentFixture<BurnConfirmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BurnConfirmComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BurnConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
