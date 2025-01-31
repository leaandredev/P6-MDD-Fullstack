import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Comment } from 'src/app/core/interfaces/comment.interface';
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

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private commentService: CommentService,
    private matSnackBar: MatSnackBar
  ) {
    this.postId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.postService.getById(this.postId).subscribe((post: PostResponse) => {
      this.post = post;
    });
    this.form = this.fb.group({
      content: ['', Validators.required],
    });
  }

  public back() {
    window.history.back();
  }

  public addComment() {
    if (this.form && this.form.valid) {
      const commentRequest = this.form.value as Comment;
      commentRequest.postId = Number(this.postId);
      commentRequest.userId = this.sessionService.sessionInformation!.id;
      this.commentService.create(commentRequest).subscribe({
        next: () => {
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
          this.matSnackBar.open('Une erreur est survenu', 'Close', {
            duration: 2000,
          });
        },
      });
    }
  }
}
