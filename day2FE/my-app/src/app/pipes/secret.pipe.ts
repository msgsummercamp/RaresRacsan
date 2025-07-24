import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'pipe',
})
export class SecretPipe implements PipeTransform {
  transform(value: string): string {
    return (
      value.substring(0, value.length / 2).toLowerCase() +
      value.substring(value.length / 2, value.length).toUpperCase()
    );
  }
}
