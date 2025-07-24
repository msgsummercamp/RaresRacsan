import { Component, inject, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButton } from '@angular/material/button';
import { HttpClient } from '@angular/common/http';
import { AuthDirective } from '../auth.directive';
import { AuthService } from '../services/auth.service';

// response type for the showRandomDog method
type DogResponse = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-home',
  imports: [CommonModule, MatButton, AuthDirective],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  // hardcoded logged-in state
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);

  public readonly data = signal('');
  public readonly loggedIn = this.authService.loggedIn;

  // hardcoded logged-in state
  public logIn(): void {
    this.authService.logIn();
  }

  public logOut(): void {
    this.authService.logOut();
  }

  async showRandomDog() {
    // the url and the image element
    const fetchUrl: string = 'https://dog.ceo/api/breeds/image/random';
    const dogImage: HTMLImageElement = document.getElementById(
      'dogImage'
    ) as HTMLImageElement;

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
      error: (err) => console.log('Error: ' + err),
    });
  }
}
