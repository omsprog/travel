import {Pageable, Sort} from "./pageable.interface";

export interface CustomerPage {
  content:          Customer[];
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

export interface Customer {
  dni:           string;
  fullName:      string;
  creditCard:    string;
  phoneNumber:   string;
  totalFlights:  number;
  totalLodgings: number;
  totalTours:    number;
}
