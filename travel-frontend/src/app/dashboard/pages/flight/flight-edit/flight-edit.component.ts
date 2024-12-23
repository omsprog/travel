import {Component, OnInit} from '@angular/core';
import {FlightService} from "../../../services/flight-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Flight} from "../../../interfaces/flight.interface";
import {AEROLINES} from "../../../constants/aerolines";

@Component({
  selector: 'app-flight-edit',
  templateUrl: './flight-edit.component.html',
  styles: []
})
export class FlightEditComponent implements OnInit {
  public loading = false;
  public aerolineOptions = AEROLINES

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

          this.flightForm.reset(flight)
          this.loading = false;
          return;
        }
      )
  }
  private fb: FormBuilder = new FormBuilder();
  public flightForm : FormGroup = this.fb.group({
    id:           [0],
    originLat:    [0],
    originLng:    [0],
    destinationLat:   [0],
    destinationLng:   [0],
    originName:   [''],
    destinationName:  [''],
    price:        [0],
    aeroLine:     [''],
  })

  get currentFlight() : Flight {
    return this.flightForm.value as Flight
  }

  onSubmit() {
    if(this.flightForm.invalid) return

    // Updating
    if(this.currentFlight.id) {
      this.flightService.updateFlight(this.currentFlight)
        .subscribe(flight => {
          this.router.navigate(['/dashboard/flights'], {
            state: {
              message: 'Flight updated successfully!'
            }
          });
        })
      return;
    }
  }
}
