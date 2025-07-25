import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _loggedIn = signal<boolean>(false);
  public readonly loggedIn = this._loggedIn.asReadonly();

  public logIn(): void {
    this._loggedIn.set(true);
  }

  public logOut(): void {
    this._loggedIn.set(false);
  }
}
