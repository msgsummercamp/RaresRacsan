import { Injectable, signal, inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

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
  public readonly loggedIn = this._loggedIn.asReadonly();
  private readonly _apiUrl = 'http://localhost:8080/api/auth/signin';

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
    localStorage.removeItem('token');
    this._loggedIn.set(false);
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }

  private setToken(token: string): void {
    localStorage.setItem('token', token);
  }
}
