import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comment } from '../interfaces/comment.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private pathService = 'api/comment';

  constructor(private httpClient: HttpClient) {}

  public create(post: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.pathService, post);
  }
}
