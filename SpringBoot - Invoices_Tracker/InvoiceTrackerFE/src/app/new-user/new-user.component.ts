import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/shared/http.service';
import { User } from 'src/shared/models/user.model';
import { API_CONST, ROLES } from 'src/shared/shared.constants';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {


  constructor( private httpService : HttpService, private router: Router) { }


   first: string =''
   last: string =''
   age: number = 0
   address: string =''
   password: string =''
   repassword: string =''
   email: string =''

   selectedOption: any =0 ;
 
   options = [
     { name: "Super User", value: ROLES.SUPERUSER },
     { name: "Auditor", value: ROLES.AUDITOR },
     { name: "User", value: ROLES.USER },
   ]

  errorMessage : string = "this is an error message to show after validation process"
  errorFlag : boolean = false
  token : any
  ngOnInit(): void {
    this.token=localStorage.getItem("token")
  }

  onSubmit(){

    this.errorFlag=false
    if(this.last==='' || this.email==='' || this.first==='' || this.age<15|| this.address==='' || (this.selectedOption >2 && this.selectedOption<0)){
      this.errorMessage="Make sure all fields filled correctly !"
      this.errorFlag=true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      let user =  new User(this.first,this.last,this.email,this.address,this.selectedOption,this.age,this.password)
      console.log(user);

      this.httpService.postWithToken(`${API_CONST.ACTIONS.SIGNUP}`, (user), this.token).toPromise().then((response) => {
        this.errorFlag=false;
        this.router.navigateByUrl("/users");

      }).catch((err: HttpErrorResponse) => {
        console.error( err.error);
        this.errorFlag=true;
        this.errorMessage=err.error;
      });
    }
  }

  selectRole(event:any) {

    let fireboxFix = event.target || event.srcElement;
    let indexToFind = (<HTMLSelectElement>fireboxFix).value;
    let testArray = [];
    for (let row of this.options) {
       testArray.push(row.name)
    }
    this.selectedOption = testArray.indexOf(indexToFind);
    console.log(this.selectedOption )
 }
}
