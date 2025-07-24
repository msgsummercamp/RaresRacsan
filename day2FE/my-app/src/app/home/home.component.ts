import { Component, inject, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButton } from '@angular/material/button';
import { HttpClient } from '@angular/common/http';
import { AuthDirective } from '../auth.directive';
import { AuthService } from '../services/auth.service';
import { SecretPipe } from '../pipes/secret.pipe';

// response type for the showRandomDog method
type DogResponse = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-home',
  imports: [CommonModule, MatButton, AuthDirective, SecretPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  // hardcoded logged-in state
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  public readonly data = signal('');
  public readonly loggedIn = this.authService.loggedIn; // kept for showing logged in message on /home (used as directive param on home.component.html)

  public async showRandomDog(): Promise<void> {
    // the url and the image element
    const fetchUrl: string = 'https://dog.ceo/api/breeds/image/random';
    const dogImage: HTMLImageElement = document.getElementById(
      'dogImage'
    ) as HTMLImageElement;
    const errorMessage: HTMLElement = document.getElementById(
      'error-message'
    ) as HTMLElement;

    // fetch the data and update the image source
    this.http.get<DogResponse>(fetchUrl).subscribe({
      next: (response) => {
        if (response.status !== 'success') {
          console.error('Failed to fetch dog image');
          return;
        }
        this.data.set(response.message);
        dogImage.src = this.data();
        dogImage.hidden = false;
      },
      // changed from console.log to an error message on screen
      error: (err) => {
        errorMessage.textContent = `Error fetching dog image: ${err.message}`;
        errorMessage.hidden = false;
      },
    });
  }
}
