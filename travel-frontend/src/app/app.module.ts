import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {authInterceptor} from "./shared/auth.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideHttpClient(
      withInterceptors([authInterceptor])
    ),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
