import {Component, OnInit} from '@angular/core';
import {FlightService} from "../../../services/flight-service.service";
import {FlightPage} from "../../../interfaces/flight.interface";

@Component({
  selector: 'dashboard-flight-list',
  templateUrl: './flight-list.component.html',
  styles: []
})
export class FlightListComponent implements OnInit {
  public flightPage? : FlightPage;
  public currentPage : number = 0;
  public loading = false;

  constructor(private flightService: FlightService) { }

  ngOnInit(): void {
    this.loading = true;
    this.flightService.getFlights()
      .subscribe(flight => {
        this.flightPage = flight;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.flightService.getFlights(page)
      .subscribe(flight => {
        this.flightPage = flight;
        this.loading = false;
      })
  }
}
