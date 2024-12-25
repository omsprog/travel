import { Component } from '@angular/core';
import {AuthService} from "../../../shared/services/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {SignInRequest} from "../../interfaces/signIn.interface";

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
  public errorMessage: string = '';
  public signInForm : FormGroup = this.fb.group({
    email: [null],
    password: [null]
  });

  get currentSignIn() : SignInRequest {
    return this.signInForm.value as SignInRequest;
  }

  onSubmit() {
    if(this.signInForm.invalid) {
      this.signInForm.markAllAsTouched()
      return;
    }

    this.authService.signIn(this.currentSignIn)
      .subscribe({
        next: () => this.router.navigate(['/dashboard']),
        error: (error : Error) => {
          this.errorMessage = error.message;
        }
      });
  }
}
