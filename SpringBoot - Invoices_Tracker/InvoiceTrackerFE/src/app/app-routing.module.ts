import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EditItemComponent } from './edit-item/edit-item.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ErrorComponent } from './error/error.component';
import { InvoicesComponent } from './invoices/invoices.component';
import { ItemsComponent } from './items/items.component';
import { NewInvoiceComponent } from './new-invoice/new-invoice.component';
import { NewItemComponent } from './new-item/new-item.component';
import { NewUserComponent } from './new-user/new-user.component';
import { PreviewInvoiceComponent } from './preview-invoice/preview-invoice.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  // { path: 'login', loadChildren: () => import('src/app/auth/auth.module').then(m => m.AuthModule) },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'invoices', component: InvoicesComponent },
  { path: 'users', component: UsersComponent },
  { path: 'items', component: ItemsComponent },
  { path: 'newUser', component: NewUserComponent },
  { path: 'editUser', component: EditUserComponent },
  { path: 'newItem', component: NewItemComponent },
  { path: 'editItem', component: EditItemComponent },
  { path: 'newInvoice', component: NewInvoiceComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'preview', component: PreviewInvoiceComponent },








];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
