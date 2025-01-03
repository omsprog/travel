import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer, CustomerPage} from "../interfaces/customer.interface";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private PAGE_SIZE = 5;
  private customerBaseUrl : string = `http://localhost:8080/api/travel/users`;

  constructor(private http: HttpClient) { }

  getCustomers(currentPage = 0) : Observable<CustomerPage> {
    let getCustomersUrl = `${this.customerBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<CustomerPage>(getCustomersUrl);
  }

  getProfileInformation() : Observable<Customer> {
    let getProfileUrl = `${this.customerBaseUrl}/profile`;
    return this.http.get<Customer>(getProfileUrl);
  }

  getProfilePicture() : Observable<Blob> {
    let getProfilePictureUrl = `${this.customerBaseUrl}/profile-picture`;
    return this.http.get(getProfilePictureUrl, {responseType: "blob"});
  }
}
