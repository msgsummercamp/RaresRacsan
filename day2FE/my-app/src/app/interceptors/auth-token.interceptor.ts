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

  const token = sessionStorage.getItem('token');
  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(cloned);
  }
  return next(req);
};
