import {Pageable, Sort} from "./pageable.interface";

export interface FlightPage {
  content:          Flight[];
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

export interface Flight {
  id:          number;
  originLat:   number;
  originLng:   number;
  destinationLat:  number;
  destinationLng:  number;
  originName:  string;
  destinationName: string;
  price:       number;
  aeroLine:    string;
}
