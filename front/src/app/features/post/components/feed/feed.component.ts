import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
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
  public orderBy: String = 'date';
  public asc: boolean = true;
  public handsetPortrait: boolean = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private sessionService: SessionService,
    private responsive: BreakpointObserver
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
    this.getFeedPosts();
  }

  public createPost(): void {
    this.router.navigate(['post/create']);
  }

  public changeOrderBy(value: String) {
    this.getFeedPosts();
  }

  public toggleSort() {
    this.asc = !this.asc;
    this.getFeedPosts();
  }

  private getFeedPosts() {
    this.userService
      .getFeed(
        this.sessionService.sessionInformation!.id.toString(),
        this.orderBy.toString(),
        this.asc
      )
      .subscribe((feedPosts: PostResponse[]) => {
        this.feedPosts = feedPosts;
      });
  }
}
