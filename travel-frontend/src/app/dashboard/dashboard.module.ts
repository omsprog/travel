import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { SidenavComponent } from './sidenav/sidenav.component';
import { LayoutComponent } from './layout/layout.component';
import { HomeComponent } from './home/home.component';
import { FlightsComponent } from './flights/flights.component';
import { HotelsComponent } from './hotels/hotels.component';


@NgModule({
  declarations: [
    SidenavComponent,
    LayoutComponent,
    HomeComponent,
    FlightsComponent,
    HotelsComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
