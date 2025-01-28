import { Injectable } from '@angular/core';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  constructor() {
    this.loadSession();
  }

  private loadSession(): void {
    const sessionData = localStorage.getItem('sessionInformation');
    if (sessionData) {
      this.sessionInformation = JSON.parse(sessionData);
      this.isLogged = true;
      this.next();
    }
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    localStorage.setItem('sessionInformation', JSON.stringify(user));
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    localStorage.removeItem('sessionInformation');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
