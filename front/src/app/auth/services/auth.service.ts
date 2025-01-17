import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { SessionInformationResponse } from '../interfaces/tokenResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(
    loginRequest: LoginRequest
  ): Observable<SessionInformationResponse> {
    return this.httpClient.post<SessionInformationResponse>(
      `${this.pathService}/login`,
      loginRequest
    );
  }
}
