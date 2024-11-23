import {Component, OnInit} from '@angular/core';
import {ReservationPage} from "../../../interfaces/reservation.interface";
import {ReservationService} from "../../../services/reservation-service.service";

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styles: []
})
export class ReservationListComponent implements OnInit {
  public reservationPage? : ReservationPage;
  public currentPage : number = 0;
  public loading = false;
  public message : string | null = null;

  constructor(private reservationService: ReservationService) { }

  ngOnInit(): void {
    const state = window.history.state;
    this.message = state?.message || null;

    if (this.message) {
      setTimeout(() => {
        this.message = null;
      }, 4000);
    }

    this.loading = true;
    this.reservationService.getReservations()
      .subscribe(reservation => {
        this.reservationPage = reservation;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.reservationService.getReservations(page)
      .subscribe(reservation => {
        this.reservationPage = reservation;
        this.loading = false;
      })
  }
}
