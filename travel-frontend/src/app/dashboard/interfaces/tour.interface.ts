import {Customer} from "./customer.interface";
import {Pageable, Sort} from "./pageable.interface";

export interface TourPage {
    content:          Tour[];
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

export interface Tour {
    id:           number;
    name:         string;
    customer:     Customer;
    tickets:      string[];
    reservations: string[];
}
