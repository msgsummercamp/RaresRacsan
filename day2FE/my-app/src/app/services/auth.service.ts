import { Injectable, signal, inject } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _loggedIn = signal<boolean>(false);
  public readonly loggedIn = this._loggedIn.asReadonly();
  private readonly router = inject(Router);

  public logIn(): void {
    this._loggedIn.set(true);
    this.router.navigate(['/home']);
  }

  public logOut(): void {
    this._loggedIn.set(false);
  }
}
