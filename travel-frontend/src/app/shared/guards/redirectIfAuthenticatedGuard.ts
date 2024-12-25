import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";

export const RedirectIfAuthenticatedGuard : CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('jwtToken')

  if(token) {
    router.navigate(['/dashboard']);
    return false;
  }

  return true;
};
