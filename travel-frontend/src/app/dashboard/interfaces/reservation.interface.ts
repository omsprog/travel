import {Hotel} from "./hotel.interface";
import {Pageable, Sort} from "./pageable.interface";

export interface ReservationPage {
  content:          Reservation[];
  pageable:         Pageable;
  last:             boolean;
  totalPages:       number;
  totalElements:    number;
  size:             number;
  number:           number;
  sort:             Sort;
  first:            boolean;
  numberOfElements: number;
  empty:            boolean;
}

export interface Reservation {
  id:                  string;
  dateTimeReservation: string;
  dateStart:           Date;
  dateEnd:             Date;
  totalDays:           number;
  price:               number;
  hotel:               Hotel;
}
