import {Flight} from "./flight.interface";
import {Pageable, Sort} from "./pageable.interface";

export interface TicketPage {
  content:          Ticket[];
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

export interface Ticket {
  id:            string;
  departureDate: string;
  arrivalDate:   string;
  purchaseDate:  Date;
  price:         number;
  flight:        Flight;
}
