import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
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

  public hotelForm = new FormGroup({
    id:     new FormControl<number>(0),
    name:   new FormControl<string>(''),
    price:  new FormControl<number>(0),
    address: new FormControl<string>(''),
    rating: new FormControl<number>(1),
  })

  get currentHotel() : Hotel {
    return this.hotelForm.value as Hotel;
  }

  onSubmit() {
    if(this.hotelForm.invalid) return

    // Adding
    this.hotelService.addHotel(this.currentHotel)
      .subscribe(hotel => {
        this.router.navigateByUrl('/dashboard/hotels');
    })
  }
}
