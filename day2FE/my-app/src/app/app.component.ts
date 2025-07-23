import { Component, inject, signal, WritableSignal } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatButton } from '@angular/material/button';
import { HttpClient } from '@angular/common/http';
import { AuthDirective } from './auth.directive';

// response type for the showRandomDog method
type DogResponse = {
  message: string;
  status: string;
};

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbar, MatButton, AuthDirective],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  // hardcoded logged-in state
  public loggedIn: WritableSignal<boolean> = signal(false);
  private readonly http = inject(HttpClient);
  public readonly data = signal('');

  // hardcoded logged-in state
  logIn() {
    this.loggedIn.set(true);
  }

  logOut() {
    this.loggedIn.set(false);
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
