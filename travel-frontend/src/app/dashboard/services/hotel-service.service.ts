import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HotelPage} from "../interfaces/hotel.interface";

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private PAGE_SIZE = 5;
  private hotelBaseUrl : string = `http://localhost:8080/travel/hotel`;

  constructor(private http: HttpClient) { }

  getHotels(currentPage = 0) : Observable<HotelPage> {
    let getHotelsUrl = `${this.hotelBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<HotelPage>(getHotelsUrl);
  }
}
