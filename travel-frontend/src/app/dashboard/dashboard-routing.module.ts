import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {HomeComponent} from "./home/home.component";
import {FlightListComponent} from "./pages/flight/flight-list/flight-list.component";
import {HotelListComponent} from "./pages/hotel/hotel-list/hotel-list.component";
import {FlightViewComponent} from "./pages/flight/flight-view/flight-view.component";
import {FlightEditComponent} from "./pages/flight/flight-edit/flight-edit.component";
import {HotelEditComponent} from "./pages/hotel/hotel-edit/hotel-edit.component";
import {FlightCreateComponent} from "./pages/flight/flight-create/flight-create.component";
import {HotelCreateComponent} from "./pages/hotel/hotel-create/hotel-create.component";
import {CustomerListComponent} from "./pages/customer/customer-list/customer-list.component";
import {TicketListComponent} from "./pages/ticket/ticket-list/ticket-list.component";
import {ReservationListComponent} from "./pages/reservation/reservation-list/reservation-list.component";
import {TourListComponent} from "./pages/tour/tour-list/tour-list.component";

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: '',
        component: HomeComponent
      },
      {
        path: 'flights',
        component: FlightListComponent
      },
      {
        path: 'flights/create',
        component: FlightCreateComponent
      },
      {
        path: 'flights/:id/view',
        component: FlightViewComponent
      },
      {
        path: 'flights/:id/edit',
        component: FlightEditComponent
      },
      {
        path: 'hotels',
        component: HotelListComponent
      },
      {
        path: 'hotels/create',
        component: HotelCreateComponent
      },
      {
        path: 'hotels/:id/edit',
        component: HotelEditComponent
      },
      {
        path: 'customers',
        component: CustomerListComponent
      },
      {
        path: 'tickets',
        component: TicketListComponent
      },
      {
        path: 'reservations',
        component: ReservationListComponent
      },
      {
        path: 'tours',
        component: TourListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
