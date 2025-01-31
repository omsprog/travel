import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DashboardRoutingModule} from './dashboard-routing.module';
import {SidenavComponent} from './sidenav/sidenav.component';
import {LayoutComponent} from './layout/layout.component';
import {HomeComponent} from './pages/home/home.component';
import {PaginationComponent} from './pagination/pagination.component';
import {FlightListComponent} from './pages/flight/flight-list/flight-list.component';
import {HotelListComponent} from './pages/hotel/hotel-list/hotel-list.component';
import {FlightViewComponent} from './pages/flight/flight-view/flight-view.component';
import {FlightEditComponent} from './pages/flight/flight-edit/flight-edit.component';
import {HotelEditComponent} from './pages/hotel/hotel-edit/hotel-edit.component';
import {HotelCreateComponent} from './pages/hotel/hotel-create/hotel-create.component';
import {FlightCreateComponent} from './pages/flight/flight-create/flight-create.component';
import {ReactiveFormsModule} from "@angular/forms";
import { CustomerListComponent } from './pages/customer/customer-list/customer-list.component';
import { TicketListComponent } from './pages/ticket/ticket-list/ticket-list.component';
import { ReservationListComponent } from './pages/reservation/reservation-list/reservation-list.component';
import { TourListComponent } from './pages/tour/tour-list/tour-list.component';
import { ProfileViewComponent } from './pages/profile/profile-view/profile-view.component';


@NgModule({
  declarations: [
    SidenavComponent,
    LayoutComponent,
    HomeComponent,
    PaginationComponent,
    FlightListComponent,
    HotelListComponent,
    FlightViewComponent,
    FlightEditComponent,
    HotelEditComponent,
    HotelCreateComponent,
    FlightCreateComponent,
    CustomerListComponent,
    TicketListComponent,
    ReservationListComponent,
    TourListComponent,
    ProfileViewComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    ReactiveFormsModule
  ]
})
export class DashboardModule { }
