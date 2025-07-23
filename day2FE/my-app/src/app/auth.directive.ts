import {
  Directive,
  ElementRef,
  inject,
  input,
  InputSignal,
} from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';

@Directive({
  selector: '[appAuth]',
})
export class AuthDirective {
  private readonly element = inject(ElementRef);
  public readonly isLoggedIn: InputSignal<boolean> = input.required<boolean>();

  constructor() {
    toObservable(this.isLoggedIn).subscribe((loggedIn) => {
      this.element.nativeElement.style.display = loggedIn ? 'block' : 'none';
      this.element.nativeElement.classList.add('secretMessage');
    });
  }
}
