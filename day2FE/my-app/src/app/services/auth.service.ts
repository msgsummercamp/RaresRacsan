import { Injectable, signal, inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

type responseType = {
  token: string;
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _loggedIn = signal<boolean>(false);
  public readonly loggedIn = this._loggedIn.asReadonly();
  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/auth/signin';

  public login(username: string, password: string): void {
    this.http
      .post<responseType>(this.apiUrl, { username, password })
      .subscribe({
        next: (response) => {
          console.log('Response: ', response);
          this._loggedIn.set(true);
          this.setToken(response.token);
          this.router.navigate(['/home']);
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
