import { Component } from '@angular/core';
import {AuthService} from "../../../shared/services/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {SingInRequest} from "../../interfaces/signIn.interface";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: ``
})
export class LoginComponent {
  constructor(private authService : AuthService,
              private router : Router
  ) { }

  private fb: FormBuilder = new FormBuilder();
  public signInForm : FormGroup = this.fb.group({
    email: [null],
    password: [null]
  });

  get currentSignIn() : SingInRequest {
    return this.signInForm.value as SingInRequest;
  }

  onSubmit() {
    if(this.signInForm.invalid) {
      this.signInForm.markAllAsTouched()
      return;
    }

    this.authService.signIn(this.currentSignIn)
      .subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: error => alert('Login failed!')
      });
  }
}
