import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private pathService = 'api/topic';

  constructor(private httpClient: HttpClient) {}

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public subscribeUser(topicId: string, userId: string): Observable<void> {
    return this.httpClient.post<void>(
      `${this.pathService}/${topicId}/subscribe/${userId}`,
      null
    );
  }

  public unsubscribeUser(topicId: string, userId: string): Observable<void> {
    return this.httpClient.delete<void>(
      `${this.pathService}/${topicId}/unsubscribe/${userId}`
    );
  }
}
