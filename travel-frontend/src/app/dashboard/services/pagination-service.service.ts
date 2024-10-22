import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FlightPage} from "../interfaces/flight.interface";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaginationService {

  private currentPage : number = 0;
  private pageSize : number = 5;
  private baseUrl : string = `http://localhost:8080/travel/flight?page=${this.currentPage}&size=${this.pageSize}`;

  constructor(private http: HttpClient) { }

  getFlights():Observable<FlightPage> {
    return this.http.get<FlightPage>(this.baseUrl);
  }
}
