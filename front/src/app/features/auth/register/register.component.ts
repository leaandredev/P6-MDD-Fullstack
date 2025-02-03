import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { RegisterRequest } from '../../../core/interfaces/registerRequest.interface';

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
      [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(40),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/), // Uppercase, lowercase, number and special caracter
      ],
    ],
  });

  constructor(
    private authService: AuthService,
    private responsive: BreakpointObserver,
    private fb: FormBuilder,
    private router: Router
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

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
      next: (_: void) => this.router.navigate(['/']),
      error: (_) => (this.onError = true),
    });
  }

  get password() {
    return this.form.get('password');
  }
}
