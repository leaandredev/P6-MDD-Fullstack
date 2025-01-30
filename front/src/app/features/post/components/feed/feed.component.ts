import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/core/interfaces/post.interface';
import { PostResponse } from 'src/app/core/interfaces/postResponse.interface';
import { SessionService } from 'src/app/core/services/session.service';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit {
  public feedPosts: PostResponse[] = [];

  constructor(
    private router: Router,
    private userService: UserService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.userService
      .getFeed(this.sessionService.sessionInformation!.id.toString())
      .subscribe((feedPosts: PostResponse[]) => {
        this.feedPosts = feedPosts;
      });
  }

  public createPost(): void {
    this.router.navigate(['post/create']);
  }
}
