import { Injectable } from '@angular/core';
import {BehaviorSubject, catchError, map, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {SignInResponse, SignInRequest} from "../../auth/interfaces/signIn.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUrl : string = 'http://localhost:8080/api/travel/users';
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) { }

  signUp(data: { username: string; password: string }) {
    return this.http.post(`${this.authBaseUrl}/signup`, data);
  }

  signIn(signIn: SignInRequest) {
    return this.http.post<SignInResponse>(`${this.authBaseUrl}/signin`, signIn)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'An unknown error occurred.';

          // Directly access the 'errors' array from the backend response
          if (error.error && error.error.errors && Array.isArray(error.error.errors)) {
            // Join the error messages from the 'errors' array into a single string
            errorMessage = error.error.errors.join('. ');
          } else if (error.error && error.error.error) {
            errorMessage = error.error.error;
          } else if (error.status === 400) {
            errorMessage = 'Bad request. Please check your input.';
          } else if (error.status === 401) {
            errorMessage = 'Invalid credentials. Please check your username and password.';
          } else if (error.status === 500) {
            errorMessage = 'A server error occurred. Please try again later.';
          }

          // Return the error message as an observable
          return throwError(() => new Error(errorMessage));
        }),
        map((response: SignInResponse) => {
          console.log('Saving JWT in local storage ...');
          console.log(response.jwtToken);
          localStorage.setItem('jwtToken', response.jwtToken);
          this.isLoggedInSubject.next(true);
          return response;
        })
      );
  }

  signOut() {
    localStorage.removeItem('jwtToken');
    this.isLoggedInSubject.next(false);
  }

  getToken() {
    return localStorage.getItem('jwtToken');
  }

  isLoggedIn() {
    return this.isLoggedInSubject.asObservable();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('jwtToken');
  }
}
