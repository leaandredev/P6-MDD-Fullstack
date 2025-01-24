import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/features/feed/interfaces/user.interface';
import { UserService } from '../../services/user.service';

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
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .subscribe((user: User) => {
        this.subscriptions = user.subscriptions || [];
      });
  }
}
