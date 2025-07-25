import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { AuthService } from '../services/auth.service';
import { MatError } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatCard } from '@angular/material/card';
import { MatCardTitle } from '@angular/material/card';
import { MatCardHeader } from '@angular/material/card';
import {
  NonNullableFormBuilder,
  Validators,
  FormControl,
  ReactiveFormsModule,
} from '@angular/forms';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login',
  imports: [
    MatButton,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    MatCard,
    MatCardHeader,
    MatCardTitle,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly authService = inject(AuthService);
  private readonly _formBuilder = inject(NonNullableFormBuilder);

  public readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [
      Validators.required,
      Validators.email,
    ]),
    password: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(4),
    ]),
  });

  public onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      this.logIn();
    }
  }

  protected logIn(): void {
    this.authService.logIn();
  }

  protected logOut(): void {
    this.authService.logOut();
  }
}
