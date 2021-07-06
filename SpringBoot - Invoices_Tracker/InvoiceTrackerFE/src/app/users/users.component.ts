import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Users } from 'src/shared/models/user.interface';
import { User } from 'src/shared/models/user.model';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private httpService: HttpService, private router: Router) { }
  token:any
  allUsers:Observable<Users[]> | undefined
  allUsersList: Users[] = []

  ngOnInit(): void {
    this.token = localStorage.getItem("token")
    this.allUsers = this.getAllUsers(this.token);
    this.allUsers.subscribe(data =>{
      for(let i=0; i<data.length;i++){
        this.allUsersList.push(data[i])
      }
    })
    console.log(this.allUsersList)
   }

    getAllUsers(token:string):Observable<Users[]>{
      return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_USERS}`, this.token);
    }
    
    navigate(event:any){
      this.router.navigateByUrl("/newUser")
    }

    deleteUser(event:any){
      let id = parseInt(event.srcElement.outerText.split(" ")[1]);
      console.log(id);
      console.log(`${API_CONST.ACTIONS.DELETE_USER+id}`)
      this.httpService.putWithToken(`${API_CONST.ACTIONS.DELETE_USER+id}`,null,this.token).subscribe(data=>{
        location.reload();
      },
      error=>{
      })
    }

    editUser(event:any){
      let id = event.srcElement.outerText.split(" ")[1];
      console.log(id);
      localStorage.setItem("editedUserId",id);
      this.router.navigateByUrl("/editUser")
    }
  
}

