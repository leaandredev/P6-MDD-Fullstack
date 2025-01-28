import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/core/interfaces/user.interface';
import { SessionService } from 'src/app/core/services/session.service';
import { UserService } from '../../core/services/user.service';
import { Topic } from '../../core/interfaces/topic.interface';
import { TopicService } from '../../core/services/topic.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit {
  private user!: User;
  public subscriptions: Topic[] = [];
  public form = this.fb.group({
    userName: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
    ],
    email: ['', [Validators.required, Validators.email]],
  });

  constructor(
    private sessionService: SessionService,
    private topicService: TopicService,
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

    this.userService
      .getSubscriptions(this.sessionService.sessionInformation!.id.toString())
      .subscribe((topics: Topic[]) => {
        this.subscriptions = topics;
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

  public unsubscribe(topicId: number, topicName: string): void {
    this.topicService
      .unsubscribeUser(
        topicId.toString(),
        this.sessionService.sessionInformation!.id.toString()
      )
      .subscribe(() => {
        this.matSnackBar.open(
          `Vous êtes désabonné du thème "${topicName}".`,
          'Close',
          {
            duration: 2000,
          }
        );
        this.subscriptions = this.subscriptions.filter(
          (topic: Topic) => topic.id !== topicId
        );
      });
  }

  public logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }
}
