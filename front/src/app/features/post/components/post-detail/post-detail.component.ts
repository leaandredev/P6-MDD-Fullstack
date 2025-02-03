import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';
import { Comment } from 'src/app/core/interfaces/comment.interface';
import { CommentResponse } from 'src/app/core/interfaces/commentResponse.interface';
import { PostResponse } from 'src/app/core/interfaces/postResponse.interface';
import { CommentService } from 'src/app/core/services/comment.service';
import { PostService } from 'src/app/core/services/post.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  public postId: string;
  public post: PostResponse | undefined;
  public form: FormGroup | undefined;
  public comments!: CommentResponse[];
  handsetPortrait: boolean = false;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private commentService: CommentService,
    private matSnackBar: MatSnackBar,
    private responsive: BreakpointObserver
  ) {
    this.postId = this.route.snapshot.paramMap.get('id')!;

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

  ngOnInit(): void {
    this.fetchDetail();
    this.fetchComments();
    this.form = this.fb.group({
      content: ['', Validators.required],
    });
  }

  public back() {
    window.history.back();
  }

  private fetchDetail() {
    this.postService.getById(this.postId).subscribe((post: PostResponse) => {
      this.post = post;
    });
  }

  private fetchComments() {
    this.postService
      .getComments(this.postId)
      .subscribe((comments: CommentResponse[]) => {
        this.comments = comments;
      });
  }

  public addComment() {
    if (this.form && this.form.valid) {
      const commentRequest = this.form.value as Comment;
      commentRequest.postId = Number(this.postId);
      commentRequest.userId = this.sessionService.sessionInformation!.id;
      this.commentService.create(commentRequest).subscribe({
        next: () => {
          this.fetchComments();
          this.matSnackBar.open(
            'Votre commentaire a bien été ajouté.',
            'Close',
            {
              duration: 2000,
            }
          );
          this.form?.reset();
          this.form?.get('content')?.setErrors(null);
        },
        error: () => {
          this.matSnackBar.open('Une erreur est survenu.', 'Close', {
            duration: 2000,
          });
        },
      });
    }
  }
}
