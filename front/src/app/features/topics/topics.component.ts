import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../core/services/topic.service';
import { SessionService } from 'src/app/core/services/session.service';
import { User } from 'src/app/core/interfaces/user.interface';
import { UserService } from '../../core/services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  public topics$ = this.topicService.all();
  public subscriptions: number[] = [];

  constructor(
    private topicService: TopicService,
    private userService: UserService,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .subscribe((user: User) => {
        this.subscriptions = user.subscriptions || [];
      });
  }

  public subscribe(topicId: number, topicName: string): void {
    this.topicService
      .subscribeUser(
        topicId.toString(),
        this.sessionService.sessionInformation!.id.toString()
      )
      .subscribe(() => {
        this.matSnackBar.open(
          `Vous êtes désormais abonné au thème "${topicName}".`,
          'Close',
          {
            duration: 2000,
          }
        );
        this.subscriptions.push(+topicId);
      });
  }
}
