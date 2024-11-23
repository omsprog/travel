import {Component, OnInit} from '@angular/core';
import {TourPage} from "../../../interfaces/tour.interface";
import {TourService} from "../../../services/tour-service.service";

@Component({
  selector: 'app-tour-list',
  templateUrl: './tour-list.component.html',
  styles: []
})
export class TourListComponent implements OnInit{
  public tourPage? : TourPage;
  public currentPage : number = 0;
  public loading = false;
  public message : string | null = null;

  constructor(private tourService: TourService) { }

  ngOnInit(): void {
    const state = window.history.state;
    this.message = state?.message || null;

    if (this.message) {
      setTimeout(() => {
        this.message = null;
      }, 4000);
    }

    this.loading = true;
    this.tourService.getTours()
      .subscribe(tour => {
        this.tourPage = tour;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.tourService.getTours(page)
      .subscribe(tour => {
        this.tourPage = tour;
        this.loading = false;
      })
  }
}
