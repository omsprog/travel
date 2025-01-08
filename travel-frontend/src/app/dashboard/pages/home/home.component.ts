import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/customer-service.service";
import {LandingMessage} from "../../interfaces/customer.interface";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: []
})
export class HomeComponent implements OnInit {
  public loading = false;
  public landingMessage : LandingMessage | null = null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loading = true;
    this.userService.getLandingMessage()
      .subscribe(message => {
        this.landingMessage = message;
        this.loading = false;
      })
  }
}
