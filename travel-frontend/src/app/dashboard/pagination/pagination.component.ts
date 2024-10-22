import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'dashboard-pagination',
  templateUrl: './pagination.component.html',
  styles: []
})
export class PaginationComponent {
  @Input()
  public first! : boolean;

  @Input()
  public last!: boolean;

  @Input()
  public currentPage! : number;

  @Input()
  public totalPages! : number;

  @Output()
  public onPageChange : EventEmitter<number> = new EventEmitter();

  public changePage(pageNumber : number) {
    this.onPageChange.emit(pageNumber);
  }
}
