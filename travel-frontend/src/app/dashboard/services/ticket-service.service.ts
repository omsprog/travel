import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HotelPage} from "../interfaces/hotel.interface";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private PAGE_SIZE = 5;
  private ticketBaseUrl : string = `http://localhost:8080/travel/tickets`;

  constructor(private http: HttpClient) { }

  getTickets(currentPage = 0) : Observable<TicketPage> {
    let getHotelsUrl = `${this.hotelBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<HotelPage>(getHotelsUrl);
  }
}
