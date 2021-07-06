import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Users } from 'src/shared/models/user.interface';
import { User } from 'src/shared/models/user.model';
import { API_CONST, ROLES } from 'src/shared/shared.constants';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {



  constructor( private httpService : HttpService, private router: Router) { }


   first: string =''
   last: string =''
   age: number = 0
   address: string =''
   password: string =''
   repassword: string =''
   email: string =''


    userId: any
    selectedOption: any =0 ;
    editedUser:any
    userEdit: any
   options = [
     { name: "Super User", value: ROLES.SUPERUSER },
     { name: "Auditor", value: ROLES.AUDITOR },
     { name: "User", value: ROLES.USER },
   ]

  errorMessage : string = "this is an error message to show after validation process"
  errorFlag : boolean = false
  token : any
  idString: any
  ngOnInit(): void {
    this.token=localStorage.getItem("token")
    this.idString=localStorage.getItem("editedUserId")
    this.userId = parseInt(this.idString);
    console.log("edited user id = "+this.userId);
    this.editedUser = this.getEditedUser(this.token,this.userId);
    this.editedUser.subscribe((data:any) =>{
      this.userEdit= new User(data.firstName,data.lastName,data.email,data.address,data.role,data.age,data.password)
      console.log(this.userEdit);

      this.first=this.userEdit.firstName;
      this.last=this.userEdit.lastName;
      this.age=this.userEdit.age;
      this.address=this.userEdit.address;
      this.email=this.userEdit.email;
    })
  }

  getEditedUser(token:string,id:number):Observable<Users>{
    return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_USERS+"/"+id}`, this.token);
  }

  onSubmit(){

    this.errorFlag=false
    if(this.last==='' || this.email==='' || this.first==='' || this.age<15|| this.address==='' || (this.selectedOption >2 && this.selectedOption<0)){
      this.errorMessage="Make sure all fields filled correctly !"
      this.errorFlag=true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      this.userEdit.firstName=this.first;
      this.userEdit.lastName=this.last
      this.userEdit.last=this.last;
      this.userEdit.address=this.address;
      this.userEdit.age=this.age;
      this.userEdit.email=this.email;
      this.userEdit.role=this.selectedOption;

      this.httpService.putWithToken(`${API_CONST.ACTIONS.EDIT_USER+"/"+this.userId}`, this.userEdit, this.token).toPromise().then(() => {
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
