import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostResponse } from 'src/app/core/interfaces/postResponse.interface';
import { PostService } from 'src/app/core/services/post.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  public postId: string;
  public post: PostResponse | undefined;

  constructor(private postService: PostService, private route: ActivatedRoute) {
    this.postId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.postService.getById(this.postId).subscribe((post: PostResponse) => {
      this.post = post;
    });
  }

  public back() {
    window.history.back();
  }
}
