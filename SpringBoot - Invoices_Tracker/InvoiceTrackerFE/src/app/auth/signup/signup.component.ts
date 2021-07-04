import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/shared/http.service';
import { User } from 'src/shared/models/user.model';
import { API_CONST, ROLES } from 'src/shared/shared.constants';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor( private httpService : HttpService, private router: Router) { }


   first: string =''
   last: string =''
   age: number = 0
   address: string =''
   password: string =''
   repassword: string =''
   email: string =''

  errorMessage : string = "this is an error message to show after validation process"
  errorFlag : boolean = false

  ngOnInit(): void {
  }

  onSubmit(){

    this.errorFlag=false
    if(this.last==='' || this.email==='' || this.first==='' || this.age<15|| this.address===''){
      this.errorMessage="Make sure all fields filled correctly !"
      this.errorFlag=true
    }
    if (this.password !== this.repassword && this.errorFlag===false){
      this.errorMessage = "Password did not match: Please try again..."
      this.errorFlag = true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      let user =  new User(this.first,this.last,this.email,this.address,ROLES.USER,this.age,this.password)
      try{

      }
      catch (e){

      }
      this.httpService.post(`${API_CONST.ACTIONS.SIGNUP}`, (user)).toPromise().then((response) => {
        this.errorFlag=false;
        this.router.navigateByUrl("/login");

      }).catch((err: HttpErrorResponse) => {
        console.error( err.error);
        this.errorFlag=true;
        this.errorMessage=err.error;
      });
    }
  }

}
