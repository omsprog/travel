import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FlightPage} from "../interfaces/flight.interface";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private PAGE_SIZE = 5;
  private flightBaseUrl : string = `http://localhost:8080/travel/flight`;

  constructor(private http: HttpClient) { }

  getFlights(currentPage = 0) : Observable<FlightPage> {
    let getFlightsUrl = `${this.flightBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<FlightPage>(getFlightsUrl);
  }
}
