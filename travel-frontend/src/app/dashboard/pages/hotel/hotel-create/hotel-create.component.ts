import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {Hotel} from "../../../interfaces/hotel.interface";
import {HotelService} from "../../../services/hotel-service.service";

@Component({
  selector: 'app-hotel-create',
  templateUrl: './hotel-create.component.html',
  styles: []
})
export class HotelCreateComponent {
  constructor(private router: Router,
              private hotelService: HotelService
  ) { }

  private fb: FormBuilder = new FormBuilder();
  public hotelForm : FormGroup = this.fb.group({
    id: [0],
    name: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    price: [0, [Validators.required, Validators.min(1)]],
    address: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    rating: [0, [Validators.required, Validators.min(1)]]
  })

  get currentHotel() : Hotel {
    return this.hotelForm.value as Hotel;
  }

  isValidField(field : string) {
    return this.hotelForm.controls[field].errors
      && this.hotelForm.controls[field].touched;
  }

  getFieldError(field : string) {
    if(!this.hotelForm.controls[field])
      return null

    const errors = this.hotelForm.controls[field].errors || {};

    for(const key of Object.keys(errors)) {
      switch(key) {
        case 'required':
          return 'Field is required';
        case 'min':
          return 'Must be positive';
        case 'minlength':
        case 'maxlength':
          return 'Must be between 4 an 30 characters';
      }
    }

    return null
  }


  onSubmit() {
    if(this.hotelForm.invalid) {
      this.hotelForm.markAllAsTouched();
      return;
    }

    // Adding
    this.hotelService.addHotel(this.currentHotel)
      .subscribe(hotel => {
        this.router.navigate(['/dashboard/hotels'], {
          state: {
            message: 'Hotel added successfully!'
          }
        });
    })
  }
}
