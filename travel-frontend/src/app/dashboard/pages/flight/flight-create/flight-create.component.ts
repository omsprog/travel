import {Component} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {FlightService} from "../../../services/flight-service.service";
import {Flight} from "../../../interfaces/flight.interface";
import {AEROLINES} from "../../../constants/aerolines";

@Component({
  selector: 'app-flight-create',
  templateUrl: './flight-create.component.html',
  styles: []
})
export class FlightCreateComponent {
  constructor(private router: Router,
              private flightService: FlightService
  ) { }

  public aerolineOptions = AEROLINES
  private fb: FormBuilder = new FormBuilder();
  public flightForm : FormGroup = this.fb.group({
    id:           [0],
    originLat:    [0],
    originLng:    [0],
    destinyLat:   [0],
    destinyLng:   [0],
    originName:   [''],
    destinyName:  [''],
    price:        [0],
    aeroLine:     [''],
  })

  get currentFlight() : Flight {
    return this.flightForm.value as Flight;
  }

  onSubmit() {
    if(this.flightForm.invalid) return

    // Adding
    this.flightService.addFlight(this.currentFlight)
      .subscribe(flight => {
        this.router.navigate(['/dashboard/flights'], {
          state: {
            message: 'Flight added successfully!'
          }
        });
      })
  }
}
