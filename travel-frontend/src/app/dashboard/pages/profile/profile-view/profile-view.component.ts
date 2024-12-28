import {Component, OnInit} from '@angular/core';
import {Customer} from "../../../interfaces/customer.interface";
import {CustomerService} from "../../../services/customer-service.service";

@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styles: ``
})
export class ProfileViewComponent implements OnInit {
  public isProfileInformationLoading = false;
  public isProfilePictureLoading = false;

  public profile? : Customer
  public imageUrl? : string

  constructor(private customerService : CustomerService) { }

  ngOnInit() : void {
    this.isProfilePictureLoading = true;
    this.isProfileInformationLoading = true;

    this.customerService.getProfilePicture().subscribe(blob => {
      this.imageUrl = URL.createObjectURL(blob);
    })

    this.customerService.getProfileInformation().subscribe(profile => {
      this.profile = profile;
      this.isProfilePictureLoading = false;
      this.isProfileInformationLoading = false;
      return;
    })
  }
}
