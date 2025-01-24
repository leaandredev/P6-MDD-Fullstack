import { Component } from '@angular/core';
import { TopicService } from '../../services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent {
  public topics$ = this.topicService.all();

  constructor(private topicService: TopicService) {}
}
