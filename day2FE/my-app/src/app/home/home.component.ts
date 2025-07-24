import { Component, inject, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthDirective } from '../auth.directive';
import { AuthService } from '../services/auth.service';
import { SecretPipe } from '../pipes/secret.pipe';

@Component({
  selector: 'app-home',
  imports: [CommonModule, AuthDirective, SecretPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private readonly authService = inject(AuthService);

  public readonly loggedIn = this.authService.loggedIn; // kept for showing logged in message on /home (used as directive param on home.component.html)
}
