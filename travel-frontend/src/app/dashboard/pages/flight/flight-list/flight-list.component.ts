import {Component, OnInit} from '@angular/core';
import {PaginationService} from "../../../services/pagination-service.service";
import {FlightPage} from "../../../interfaces/flight.interface";

@Component({
  selector: 'app-flight-list',
  templateUrl: './flight-list.component.html',
  styles: []
})
export class FlightListComponent implements OnInit {
  public flightPage? : FlightPage;

  constructor(private paginationService: PaginationService) { }

  ngOnInit(): void {
    this.paginationService.getFlights()
      .subscribe(flight => {
        this.flightPage = flight;
      })
  }
}
