import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {HomeComponent} from "./home/home.component";
import {FlightListComponent} from "./pages/flight/flight-list/flight-list.component";
import {HotelListComponent} from "./pages/hotel/hotel-list/hotel-list.component";

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
        path: 'hotels',
        component: HotelListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
