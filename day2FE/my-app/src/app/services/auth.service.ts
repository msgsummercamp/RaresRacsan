import { Injectable, signal, inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

type ResponseType = {
  token: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _loggedIn = signal<boolean>(false);
  private readonly _router = inject(Router);
  private readonly _http = inject(HttpClient);
  private readonly _apiUrl = environment.apiUrl;

  public readonly loggedIn = this._loggedIn.asReadonly();

  constructor() {
    this.checkAuthState();
  }

  private checkAuthState(): void {
    const token = sessionStorage.getItem('token');
    if (token) {
      this._loggedIn.set(true);
    }
  }

  public login(username: string, password: string): void {
    this._http
      .post<ResponseType>(this._apiUrl, { username, password })
      .subscribe({
        next: (response) => {
          this._loggedIn.set(true);
          this.setToken(response.token);
          this._router.navigate(['/home']);
        },
        error: (err) => console.error(err),
      });
  }

  public logout(): void {
    sessionStorage.removeItem('token');
    this._loggedIn.set(false);
  }

  public getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  private setToken(token: string): void {
    sessionStorage.setItem('token', token);
  }
}
