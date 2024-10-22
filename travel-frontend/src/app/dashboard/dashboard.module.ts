import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DashboardRoutingModule} from './dashboard-routing.module';
import {SidenavComponent} from './sidenav/sidenav.component';
import {LayoutComponent} from './layout/layout.component';
import {HomeComponent} from './home/home.component';
import {HotelsComponent} from './hotels/hotels.component';
import {PaginationComponent} from './pagination/pagination.component';
import { FlightListComponent } from './pages/flight/flight-list/flight-list.component';


@NgModule({
  declarations: [
    SidenavComponent,
    LayoutComponent,
    HomeComponent,
    HotelsComponent,
    PaginationComponent,
    FlightListComponent,
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
