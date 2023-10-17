import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { Erro404Component } from './pages/erro404/erro404.component';
import { AppMaterialModule } from './shared/app-material/app-material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidenavComponent } from './pages/home/sidenav/sidenav.component';
import { FooterComponent } from './pages/home/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    Erro404Component,
    SidenavComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppMaterialModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
