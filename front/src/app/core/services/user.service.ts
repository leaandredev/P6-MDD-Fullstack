import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'api/user';

  constructor(private httpClient: HttpClient) {}

  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public update(user: User): Observable<User> {
    console.log(user);

    return this.httpClient.put<User>(`${this.pathService}/${user.id}`, user);
  }

  public getSubscriptions(id: string): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(
      `${this.pathService}/${id}/subscriptions`
    );
  }
}
