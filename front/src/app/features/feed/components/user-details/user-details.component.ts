import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/features/feed/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
  private user!: User;
  public form = this.fb.group({
    userName: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
    ],
    email: ['', [Validators.required, Validators.email]],
  });

  constructor(
    private sessionService: SessionService,
    private fb: FormBuilder,
    private userService: UserService,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .subscribe((user: User) => {
        this.user = user;
        this.initForm(user);
      });
  }

  public initForm(user: User) {
    this.form.patchValue({
      userName: user.userName,
      email: user.email,
    });
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const updatedUser = { ...this.user, ...this.form.value } as User;
      this.userService.update(updatedUser).subscribe((userUpdated: User) => {
        this.matSnackBar.open(
          'Informations sauvegardées, veuillez vous reconnecter.',
          'Close',
          {
            duration: 2000,
          }
        );
        this.logOut();
      });
    }
  }

  public logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }
}
