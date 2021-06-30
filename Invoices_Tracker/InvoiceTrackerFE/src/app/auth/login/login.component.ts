import {Component, OnDestroy} from '@angular/core';
import {AuthService} from '../auth.service';
import {Login} from './login.model';
import {Subscription} from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnDestroy {

  // constructor(private authService: AuthService) {
  // }

  constructor(private router: Router) { }

  ngOnDestroy(): void {
    throw new Error('Method  Commented Below .');
  }

  
  // msgs: Message[] = [];
  loginRequest: Login = new Login('', '');
  content = true;
  subscriptions: Subscription[] = [];

  // login() {
  //   this.msgs = [];
  //   this.subscriptions.push(
  //     this.authService.login(this.loginRequest).subscribe(
  //       data => {
  //       this.authService.storeToken(data);
  //       this.getUserInformation();
  //       this.hideMsgs();
  //     },
  //     error => {
  //       this.showError('Incorrect Username or Password!');
  //     }));
  // }

  // getUserInformation() {
  //   this.subscriptions.push(
  //     this.authService.getUserInformation().subscribe(
  //       (data: User) => {
  //         this.authService.saveUser(data);
  //         this.authService.routeUser();
  //       }));
  // }

  // resetPassword() {
  //   this.msgs = [];
  //   this.subscriptions.push(
  //     this.authService.resetPassword(this.loginRequest.username).subscribe(
  //       data => {
  //       this.hideMsgs();
  //       this.showSuccessMessage();
  //     },
  //     error => {
  //       this.showError('Incorrect Username!');
  //     }));
  // }

  // showSuccessMessage() {
  //   this.msgs = [];
  //   this.msgs.push({severity: 'success', summary: 'Email Sent: ', detail: 'Check your Email to reset Password!'});
  // }

  // showError(msg: string) {
  //   this.msgs = [];
  //   this.msgs.push({severity: 'warn', summary: 'Error: ', detail: msg});
  // }

  // hideMsgs() {
  //   this.msgs = [];
  // }

  // ngOnDestroy() {
  //   this.subscriptions.forEach(s => s.unsubscribe());
  // }
}