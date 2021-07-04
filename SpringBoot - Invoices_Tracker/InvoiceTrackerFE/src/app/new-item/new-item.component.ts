import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/shared/http.service';
import { Item } from 'src/shared/models/Item.model';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-new-item',
  templateUrl: './new-item.component.html',
  styleUrls: ['./new-item.component.css']
})
export class NewItemComponent implements OnInit {
  price:any
  name:any
  errorFlag:any
  errorMessage:any
  constructor( private httpService : HttpService, private router: Router) { }
  token:any
  ngOnInit(): void {
    this.token = localStorage.getItem("token")
  }

  onSubmit(){

    this.errorFlag=false
    if(this.name==='' || this.price==undefined ){
      this.errorMessage="Make sure all fields filled correctly !"
      this.errorFlag=true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      let item =  new Item(this.name,this.price)
      console.log(item);

      this.httpService.postWithToken(`${API_CONST.ACTIONS.ADD_ITEM}`, (item), this.token).toPromise().then((response) => {
        this.errorFlag=false;
        this.router.navigateByUrl("/items");

      }).catch((err: HttpErrorResponse) => {
        console.error( err.error);
        this.errorFlag=true;
        this.errorMessage=err.error;
      });
    }
  }

}
