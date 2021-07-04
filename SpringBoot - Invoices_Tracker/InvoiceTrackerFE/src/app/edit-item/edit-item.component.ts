import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Items } from 'src/shared/models/item.interface';
import { Item } from 'src/shared/models/Item.model';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['./edit-item.component.css']
})
export class EditItemComponent implements OnInit {


  constructor( private httpService : HttpService, private router: Router) { }


   name: string =''
   price: any
 


    ItemId: any
    editedItem:any
    itemEdit: any


  errorMessage : string = "this is an error message to show after validation process"
  errorFlag : boolean = false
  token : any
  idString: any
  ngOnInit(): void {
    this.token=localStorage.getItem("token")
    this.idString=localStorage.getItem("editedUserId")
    this.ItemId = parseInt(this.idString);
    console.log("edited item id = "+this.ItemId);
    this.editedItem = this.getEditedItem(this.token,this.ItemId);
    this.editedItem.subscribe((data:any) =>{
      this.itemEdit= new Item(data.name,data.price)
      console.log(this.itemEdit);

      this.name=this.itemEdit.name;
      this.price=this.itemEdit.price;

    })
  }

  getEditedItem(token:string,id:number):Observable<Items>{
    return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_ITEMS+"/"+id}`, this.token);
  }

  onSubmit(){

    this.errorFlag=false
    if(this.name==='' || this.price==null || this.price== undefined){
      this.errorMessage="Make sure all fields filled correctly !"
      this.errorFlag=true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      this.itemEdit.name=this.name;
      this.itemEdit.price=this.price;
      console.log(this.itemEdit)

      this.httpService.putWithToken(`${API_CONST.ACTIONS.EDIT_ITEM+"/"+this.ItemId}`, this.itemEdit, this.token).toPromise().then(() => {
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
