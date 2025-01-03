import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TourPage} from "../interfaces/tour.interface";

@Injectable({
  providedIn: 'root'
})
export class TourService {

  private PAGE_SIZE = 5;
  private tourBaseUrl : string = `http://localhost:8080/api/travel/tours`;

  constructor(private http: HttpClient) { }

  getTours(currentPage = 0) : Observable<TourPage> {
    let getToursUrl = `${this.tourBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<TourPage>(getToursUrl);
  }
}
