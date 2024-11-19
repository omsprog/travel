import {Component, OnInit} from '@angular/core';
import {FlightService} from "../../../services/flight-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {Flight} from "../../../interfaces/flight.interface";

@Component({
  selector: 'app-flight-view',
  templateUrl: './flight-view.component.html',
  styles: []
})
export class FlightViewComponent implements OnInit {
  public loading = false;

  public flight? : Flight

  constructor(private flightService: FlightService,
              private activatedRoute: ActivatedRoute,
              private router: Router
  ) { }

  ngOnInit() : void {
    this.loading = true;

    this.activatedRoute.params
      .pipe(
        switchMap( ({ id }) => this.flightService.getFlightById(id) )
      )
      .subscribe(
        flight => {
          if(!flight){
            return this.router.navigateByUrl('/dashboard/flights');
          }

          this.flight = flight
          console.log(flight);
          this.loading = false;
          return;
        }
      )
  }
}
