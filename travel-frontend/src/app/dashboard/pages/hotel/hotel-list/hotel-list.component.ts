import {Component, OnInit} from '@angular/core';
import {HotelService} from "../../../services/hotel-service.service";
import {HotelPage} from "../../../interfaces/hotel.interface";

@Component({
  selector: 'dashboard-hotel-list',
  templateUrl: './hotel-list.component.html',
  styles: []
})
export class HotelListComponent implements OnInit {
  public hotelPage? : HotelPage;
  public currentPage : number = 0;
  public loading = false;

  constructor(private hotelService: HotelService) { }

  ngOnInit(): void {
    this.loading = true;
    this.hotelService.getHotels()
      .subscribe(hotel => {
        this.hotelPage = hotel;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.hotelService.getHotels(page)
      .subscribe(hotel => {
        this.hotelPage = hotel;
        this.loading = false;
      })
  }
}
