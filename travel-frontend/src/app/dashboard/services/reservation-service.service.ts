import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ReservationPage} from "../interfaces/reservation.interface";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private PAGE_SIZE = 5;
  private reservationBaseUrl : string = `http://localhost:8080/api/travel/reservations`;

  constructor(private http: HttpClient) { }

  getReservations(currentPage = 0) : Observable<ReservationPage> {
    let getReservationsUrl = `${this.reservationBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<ReservationPage>(getReservationsUrl);
  }
}
