import {Component, OnInit} from '@angular/core';
import {HotelService} from "../../../services/hotel-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";
import {Hotel} from "../../../interfaces/hotel.interface";

@Component({
  selector: 'app-hotel-edit',
  templateUrl: './hotel-edit.component.html',
  styles: []
})
export class HotelEditComponent implements OnInit {
  public loading = false;

  constructor(private hotelService: HotelService,
              private activatedRoute: ActivatedRoute,
              private router: Router
  ) { }

  ngOnInit() : void {
    this.loading = true;

    this.activatedRoute.params
      .pipe(
        switchMap( ({ id }) => this.hotelService.getHotelById(id) )
      )
      .subscribe(
        hotel => {
          if(!hotel){
            return this.router.navigateByUrl('/dashboard/hotels');
          }

          this.hotelForm.reset(hotel)
          console.log(hotel);
          this.loading = false;
          return;
        }
      )
  }

  public hotelForm = new FormGroup({
    id:     new FormControl<number>(0),
    name:   new FormControl<string>(''),
    price:  new FormControl<number>(0),
    address: new FormControl<string>(''),
    rating: new FormControl<number>(0),
  })

  get currentHotel() : Hotel {
    return this.hotelForm.value as Hotel
  }

  onSubmit() {
    if(this.hotelForm.invalid) return

    // Updating
    if(this.currentHotel.id) {
      this.hotelService.updateHotel(this.currentHotel)
        .subscribe(hero => {
          this.router.navigate(['/dashboard/hotels'], {
            state: {
              message: 'Hotel updated successfully!'
            }
          });
        })
      return;
    }
  }
}
