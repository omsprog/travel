import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {FlightService} from "../../../services/flight-service.service";
import {Flight} from "../../../interfaces/flight.interface";

@Component({
  selector: 'app-flight-create',
  templateUrl: './flight-create.component.html',
  styles: []
})
export class FlightCreateComponent {
  constructor(private router: Router,
              private flightService: FlightService,
  ) { }

  public aerolineOptions = ['aero_gold', 'blue_sky']

  public flightForm = new FormGroup({
    id:     new FormControl<number>(0),
    originLat:   new FormControl<number>(0),
    originLng:  new FormControl<number>(0),
    destinyLat:  new FormControl<number>(0),
    destinyLng: new FormControl<number>(0),
    originName: new FormControl<string>(''),
    destinyName: new FormControl<string>(''),
    price: new FormControl<number>(0),
    aeroLine: new FormControl<string>(''),
  })

  get currentFlight() : Flight {
    return this.flightForm.value as Flight;
  }

  onSubmit() {
    if(this.flightForm.invalid) return

    // Adding
    this.flightService.addFlight(this.currentFlight)
      .subscribe(flight => {
        this.router.navigateByUrl('/dashboard/flights');
      })
  }
}
