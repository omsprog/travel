import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TicketPage} from "../interfaces/ticket.interface";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private PAGE_SIZE = 5;
  private ticketBaseUrl : string = `http://localhost:8080/travel/tickets`;

  constructor(private http: HttpClient) { }

  getTickets(currentPage = 0) : Observable<TicketPage> {
    let getTicketsUrl = `${this.ticketBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<TicketPage>(getTicketsUrl);
  }
}
