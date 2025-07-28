import {
  Component,
  inject,
  signal,
  effect,
  ViewChild,
  ElementRef,
} from '@angular/core';
import { RandomdogService } from '../services/randomdog.service';
import { MatButton } from '@angular/material/button';
import { MatCard } from '@angular/material/card';

@Component({
  selector: 'app-random-dog',
  imports: [MatButton, MatCard],
  templateUrl: './random-dog.component.html',
  styleUrl: './random-dog.component.scss',
})
export class RandomDogComponent {
  private readonly _randomDogService = inject(RandomdogService);
  private readonly _data = signal('');

  @ViewChild('dogImage', { static: false })
  dogImageRef!: ElementRef<HTMLImageElement>;

  @ViewChild('errorMessage', { static: false })
  errorMessageRef!: ElementRef<HTMLElement>;

  constructor() {
    effect(() => {
      const imageUrl = this._data();
      if (imageUrl) {
        if (imageUrl.startsWith('Error')) {
          if (this.errorMessageRef) {
            this.errorMessageRef.nativeElement.textContent = imageUrl;
            this.errorMessageRef.nativeElement.style.display = 'block';
          }
          if (this.dogImageRef) {
            this.dogImageRef.nativeElement.style.display = 'none';
          }
        } else {
          if (this.dogImageRef) {
            this.dogImageRef.nativeElement.src = imageUrl;
            this.dogImageRef.nativeElement.style.display = 'block';
          }
          if (this.errorMessageRef) {
            this.errorMessageRef.nativeElement.style.display = 'none';
          }
        }
      }
    });
  }

  public async showRandomDog(): Promise<void> {
    try {
      const imageUrl = await this._randomDogService.showRandomDog();
      this._data.set(imageUrl);
    } catch (error) {
      this._data.set('Error:' + error);
    }
  }
}
