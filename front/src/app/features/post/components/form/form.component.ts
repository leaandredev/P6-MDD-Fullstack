import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Post } from 'src/app/core/interfaces/post.interface';
import { PostService } from 'src/app/core/services/post.service';
import { SessionService } from 'src/app/core/services/session.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss'],
})
export class FormComponent implements OnInit {
  public topics$ = this.topicService.all();
  public onError: boolean = false;
  public form: FormGroup | undefined;

  constructor(
    private topicService: TopicService,
    private fb: FormBuilder,
    private sessionService: SessionService,
    private postService: PostService,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      topicId: ['', Validators.required],
      title: ['', [Validators.required, Validators.maxLength(100)]],
      content: ['', Validators.required],
    });
  }

  public back() {
    window.history.back();
  }

  public submit() {
    if (this.form && this.form.valid) {
      const postRequest = this.form.value as Post;
      postRequest.userId = this.sessionService.sessionInformation!.id;
      this.postService.create(postRequest).subscribe({
        next: () => {
          this.matSnackBar.open('Votre article a bien été créé.', 'Close', {
            duration: 2000,
          });
          this.router.navigate(['/post/feed']);
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
