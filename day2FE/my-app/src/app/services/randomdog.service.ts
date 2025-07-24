import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { first, firstValueFrom, Observable } from 'rxjs';

type DogResponse = {
  message: string;
  status: string;
};

@Injectable({
  providedIn: 'root',
})
export class RandomdogService {
  private readonly http = inject(HttpClient);

  private readonly fetchUrl: string = 'https://dog.ceo/api/breeds/image/random';

  public async showRandomDog(): Promise<string> {
    try {
      const response = await firstValueFrom(
        this.http.get<DogResponse>(this.fetchUrl)
      );
      if (response.status !== 'success') {
        return 'Error fetching random dog';
      }
      return response.message;
    } catch (error) {
      return 'Error fetching random dog' + error;
    }
  }
}
