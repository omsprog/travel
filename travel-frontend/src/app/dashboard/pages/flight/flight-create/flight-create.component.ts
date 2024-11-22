import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
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
    originLat:    [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    originLng:    [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    destinyLat:   [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
    destinyLng:   [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    originName:   ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    destinyName:  ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    price:        [null, [Validators.required, Validators.min(40)]],
    aeroLine:     ['', [Validators.required]],
  })

  isValidField(field : string) {
    return this.flightForm.controls[field].errors
      && this.flightForm.controls[field].touched;
  }

  getFieldError(field : string) {
    if(!this.flightForm.controls[field])
      return null

    const errors = this.flightForm.controls[field].errors || {};

    for(const key of Object.keys(errors)) {
      switch(key) {
        case 'required':
          return 'Field is required';
        case 'min':
        case 'max':
          return 'Must be between -90 and 90';
        case 'minlength':
        case 'maxlength':
          return 'Must be between 4 an 30 characters';
      }
    }

    return null
  }

  get currentFlight() : Flight {
    return this.flightForm.value as Flight;
  }

  onSubmit() {
    if(this.flightForm.invalid) {
      this.flightForm.markAllAsTouched()
      return;
    }

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
