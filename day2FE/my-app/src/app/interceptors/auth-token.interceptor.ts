import { HttpInterceptorFn } from '@angular/common/http';

export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {
  const skipAuth = ['dog.ceo'];

  // checking if the current request contains the skipAuth
  const shouldSkipAuth = skipAuth.some((skipAuth) =>
    req.url.includes(skipAuth)
  );

  if (shouldSkipAuth) {
    return next(req);
  }

  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer token-placeholder',
    },
  });
  return next(cloned);
};
