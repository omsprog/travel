import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Flight, FlightPage} from "../interfaces/flight.interface";
import {catchError, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private PAGE_SIZE = 5;
  private flightBaseUrl : string = `http://localhost:8080/travel/flights`;

  constructor(private http: HttpClient) { }

  getFlights(currentPage = 0) : Observable<FlightPage> {
    let getFlightsUrl = `${this.flightBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<FlightPage>(getFlightsUrl);
  }

  getFlightById(id : string) : Observable<Flight | undefined> {
    return this.http.get<Flight>(`${this.flightBaseUrl}/${id}`)
      .pipe(
        catchError(error => of(undefined))
      );
  }

  updateFlight(flight : Flight) : Observable<Flight> {
    if ( !flight.id ) throw Error('Flight id is required');
    return this.http.put<Flight>(`${this.flightBaseUrl}/${flight.id}`, flight);
  }

  addFlight(flight : Flight) : Observable<Flight> {
    return this.http.post<Flight>(`${this.flightBaseUrl}`, flight);
  }
}
