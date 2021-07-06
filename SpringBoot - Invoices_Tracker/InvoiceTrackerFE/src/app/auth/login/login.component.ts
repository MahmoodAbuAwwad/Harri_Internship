import {Component, OnDestroy} from '@angular/core';
import {AuthService} from '../auth.service';
import {Login} from './login.model';
import {Subscription} from 'rxjs';
import { Router } from '@angular/router';
import { HttpService } from 'src/shared/http.service';
import { API_CONST } from 'src/shared/shared.constants';
import { HttpErrorResponse } from '@angular/common/http';
import { Token } from './token.model';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

 

  constructor( private httpService : HttpService, private router: Router) { }


  // msgs: Message[] = [];
  loginRequest: Login = new Login('', '');
  content = true;
  subscriptions: Subscription[] = [];
  badCredentials=false
  
  password: string =''
  email: string =''

  onSubmit(){
    let  loginCred = new Login(this.email,this.password);
    // console.log(loginCred)
    // this.httpService.post(`${API_CONST.ACTIONS.LOGIN}`, (loginCred)).toPromise().then((token) => {
   

    // }).catch((err: HttpErrorResponse) => {
    //   console.error( err.error);
    //   this.badCredentials=true;
    // });
    
    this.httpService.post(`${API_CONST.ACTIONS.LOGIN}`, (loginCred)).subscribe((response : any) =>{
      console.log(response)
      this.badCredentials=false;
      this.getUserInformation(response.token)
      localStorage.setItem ('token', response.token);

      this.router.navigateByUrl("/invoices");
    },
    error=>{
      console.error( error.error);
      this.badCredentials=true;
    })
     
  }


  getUserInformation(token :string) {
    this.httpService.getWithToken(`${API_CONST.ACTIONS.LOGGED_IN}`,token).subscribe((user:any)=>{
      console.log(user);
      localStorage.setItem ('email', user.email);
      localStorage.setItem ('role', user.role);
      localStorage.setItem ('id', user.id);
    },
    error=>{
      console.error( error.error);
      this.badCredentials=true;
    });
  }


}