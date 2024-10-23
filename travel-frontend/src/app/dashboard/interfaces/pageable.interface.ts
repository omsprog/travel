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
