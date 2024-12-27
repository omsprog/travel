import { Component } from '@angular/core';
import {AuthService} from "../../shared/services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styles: []
})
export class LayoutComponent {
  constructor(private authService : AuthService,
              private router : Router
  ) { }

  onLogout() {
    this.authService.signOut()
    this.router.navigate(['/auth/login']);
  }
}
