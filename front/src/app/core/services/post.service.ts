import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post } from '../interfaces/post.interface';
import { Observable } from 'rxjs';
import { PostResponse } from '../interfaces/postResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private pathService = 'api/post';

  constructor(private httpClient: HttpClient) {}

  public create(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(this.pathService, post);
  }

  public getById(id: string): Observable<PostResponse> {
    return this.httpClient.get<PostResponse>(`${this.pathService}/${id}`);
  }
}
