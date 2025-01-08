import {Component, OnInit} from '@angular/core';
import {CustomerPage} from "../../../interfaces/customer.interface";
import {UserService} from "../../../services/customer-service.service";

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styles: []
})
export class CustomerListComponent implements OnInit {
  public customerPage? : CustomerPage;
  public currentPage : number = 0;
  public loading = false;

  constructor(private customerService: UserService) { }

  ngOnInit(): void {
    this.loading = true;
    this.customerService.getCustomers()
      .subscribe(customer => {
        this.customerPage = customer;
        this.loading = false;
      })
  }

  onChangePage(page: number) {
    this.currentPage = page;
    this.loading = true;
    this.customerService.getCustomers(page)
      .subscribe(customer => {
        this.customerPage = customer;
        this.loading = false;
      })
  }
}
