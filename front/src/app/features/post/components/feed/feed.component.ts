import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  public createPost(): void {
    this.router.navigate(['post/create']);
  }
}
