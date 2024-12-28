import {Component, OnInit} from '@angular/core';
import {Customer} from "../../../interfaces/customer.interface";
import {CustomerService} from "../../../services/customer-service.service";

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styles: ``
})
export class ProfileViewComponent implements OnInit {
  public loading = false;

  public profile? : Customer

  constructor(private customerService : CustomerService) { }

  ngOnInit() : void {
    this.loading = true;

    this.customerService.getProfile().subscribe(profile => {
      this.profile = profile;
      this.loading = false;
      return;
    })
  }
}
