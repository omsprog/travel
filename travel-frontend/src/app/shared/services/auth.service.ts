import { Injectable } from '@angular/core';
import {BehaviorSubject, map} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {SignInResponse, SingInRequest} from "../../auth/interfaces/signIn.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUrl : string = 'http://localhost:8080/travel/users';
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) { }

  signUp(data: { username: string; password: string }) {
    return this.http.post(`${this.authBaseUrl}/signup`, data);
  }

  signIn(signIn : SingInRequest) {
    return this.http.post<SignInResponse>(`${this.authBaseUrl}/signin`, signIn).pipe(
      map(response => {
        console.log('Saving JWT in local storage ...')
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
