import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  public handsetPortrait: boolean = false;
  public onError: boolean = false;
  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    userName: [
      '',
      [Validators.required, Validators.min(3), Validators.max(20)],
    ],
    password: [
      '',
      [Validators.required, Validators.min(3), Validators.max(40)],
    ],
  });

  constructor(
    private responsive: BreakpointObserver,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.responsive
      .observe([Breakpoints.HandsetPortrait])
      .subscribe((result) => {
        if (result.matches) {
          console.log('Mon téléphone est en mode portrait');
          this.handsetPortrait = true;
        } else {
          this.handsetPortrait = false;
        }
      });
  }

  public back() {
    window.history.back();
  }
}
