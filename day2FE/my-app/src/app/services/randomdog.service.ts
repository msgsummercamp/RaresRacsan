import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

type DogResponse = {
  message: string;
  status: string;
};

@Injectable({
  providedIn: 'root',
})
export class RandomdogService {
  private readonly _http = inject(HttpClient);

  private readonly _fetchUrl: string =
    'https://dog.ceo/api/breeds/image/random';

  public async showRandomDog(): Promise<string> {
    try {
      const response = await firstValueFrom(
        this._http.get<DogResponse>(this._fetchUrl)
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
