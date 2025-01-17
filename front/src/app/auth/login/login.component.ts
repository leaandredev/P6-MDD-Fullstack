import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformationResponse } from '../interfaces/sessionInformationResponse.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  public handsetPortrait: boolean = false;
  public onError: boolean = false;
  public form = this.fb.group({
    identifier: [
      '',
      [Validators.required, Validators.min(3), Validators.max(20)],
    ],
    password: [
      '',
      [Validators.required, Validators.min(3), Validators.max(40)],
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
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (sessionInformationResponse: SessionInformationResponse) => {
        localStorage.setItem('token', sessionInformationResponse.token);
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.onError = true;
      },
    });
  }
}
