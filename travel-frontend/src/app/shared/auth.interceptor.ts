import {HttpInterceptorFn,} from '@angular/common/http';

export const authInterceptor : HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('jwtToken');
  console.log('INTERCEPTING request...');
  if (token) {
    console.log('TOKEN IS HERE');
    const cloned = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });
    return next(cloned);
  } else {
    console.log('TOKEN IS NOT HERE');
  }
  return next(req);
}
