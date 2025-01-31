import {Component, OnInit} from '@angular/core';
import {TicketPage} from "../../../interfaces/ticket.interface";
import {TicketService} from "../../../services/ticket-service.service";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styles: []
})
export class TicketListComponent implements OnInit {
  public ticketPage? : TicketPage;
  public currentPage : number = 0;
  public loading = false;
  public message : string | null = null;

  constructor(private ticketService: TicketService) { }

  ngOnInit(): void {
    const state = window.history.state;
    this.message = state?.message || null;

    if (this.message) {
      setTimeout(() => {
        this.message = null;
      }, 4000);
    }

    this.loading = true;
    this.ticketService.getTickets()
      .subscribe(ticket => {
        this.ticketPage = ticket;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.ticketService.getTickets(page)
      .subscribe(ticket => {
        this.ticketPage = ticket;
        this.loading = false;
      })
  }
}
