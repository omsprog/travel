import {Pageable, Sort} from "./pageable.interface";

export interface HotelPage {
  content:          Hotel[];
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

export interface Hotel {
  id:      number;
  name:    string;
  address: string;
  rating:  number;
  price:   number;
}
