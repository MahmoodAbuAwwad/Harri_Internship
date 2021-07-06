import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthService } from './auth/auth.service';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ItemsComponent } from './items/items.component';
import { UsersComponent } from './users/users.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { NewItemComponent } from './new-item/new-item.component';
import { NewUserComponent } from './new-user/new-user.component';
import { NewInvoiceComponent } from './new-invoice/new-invoice.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { EditItemComponent } from './edit-item/edit-item.component';
import { ErrorComponent } from './error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    DashboardComponent,
    NavbarComponent,
    ItemsComponent,
    UsersComponent,
    InvoicesComponent,
    NewItemComponent,
    NewUserComponent,
    NewInvoiceComponent,
    EditUserComponent,
    EditItemComponent,
    ErrorComponent,


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    

  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }