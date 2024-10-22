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
  destinyLat:  number;
  destinyLng:  number;
  originName:  string;
  destinyName: string;
  price:       number;
  aeroLine:    string;
}

export interface Pageable {
  sort:       Sort;
  pageNumber: number;
  pageSize:   number;
  offset:     number;
  paged:      boolean;
  unpaged:    boolean;
}

export interface Sort {
  sorted:   boolean;
  empty:    boolean;
  unsorted: boolean;
}
