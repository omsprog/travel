import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, of} from "rxjs";
import {Hotel, HotelPage} from "../interfaces/hotel.interface";

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private PAGE_SIZE = 5;
  private hotelBaseUrl : string = `http://localhost:8080/travel/hotels`;

  constructor(private http: HttpClient) { }

  getHotels(currentPage = 0) : Observable<HotelPage> {
    let getHotelsUrl = `${this.hotelBaseUrl}?page=${currentPage}&size=${this.PAGE_SIZE}`;
    return this.http.get<HotelPage>(getHotelsUrl);
  }

  getHotelById(id : string) : Observable<Hotel | undefined> {
    return this.http.get<Hotel>(`${this.hotelBaseUrl}/${id}`)
      .pipe(
        catchError(error => of(undefined))
      );
  }

  updateHotel(hotel : Hotel) : Observable<Hotel> {
    if ( !hotel.id ) throw Error('Hotel id is required');
    return this.http.put<Hotel>(`${this.hotelBaseUrl}/${hotel.id}`, hotel);
  }

  addHotel(hotel : Hotel) : Observable<Hotel> {
    return this.http.post<Hotel>(`${this.hotelBaseUrl}`, hotel);
  }
}
