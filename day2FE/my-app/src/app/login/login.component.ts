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
import { Router } from '@angular/router';

type LoginForm = {
  username: FormControl<string>;
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
  private readonly router = inject(Router);

  private username: string = '';
  private password: string = '';

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    username: this._formBuilder.control('', [Validators.required]),
    password: this._formBuilder.control('', [
      Validators.required,
      Validators.minLength(4),
    ]),
  });

  public onLogin(): void {
    this.username = this.loginFormGroup.controls.username.value;
    this.password = this.loginFormGroup.controls.password.value;
    this.authService.login(this.username, this.password);
  }
}
