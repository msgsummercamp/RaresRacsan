import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err) {
        console.error(`HttpError ${err.status}: ${err.message}`);
      }
      return throwError(() => err);
    })
  );
};
