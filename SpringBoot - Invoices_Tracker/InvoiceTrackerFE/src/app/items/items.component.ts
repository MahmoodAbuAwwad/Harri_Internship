import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Items } from 'src/shared/models/item.interface';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  constructor(private httpService: HttpService, private router: Router) { }
  token:any
  allItems:Observable<Items[]> | undefined
  allItemsList: Items[] = []

  ngOnInit(): void {
    this.token = localStorage.getItem("token")
    this.allItems = this.getAllItems(this.token);
    this.allItems.subscribe(data =>{
      for(let i=0; i<data.length;i++){
        this.allItemsList.push(data[i])
      }
    })
    console.log(this.allItemsList)
   }

   getAllItems(token:string):Observable<Items[]>{
    return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_ITEMS}`, token);
  }

  navigate(event:any){
    this.router.navigateByUrl("/newItem")
  }
  deleteItem(event:any){
    let id = parseInt(event.srcElement.outerText.split(" ")[1]);
    console.log(id);
    console.log(`${API_CONST.ACTIONS.DELETE_ITEM+id}`)
    this.httpService.deleteWithToken(`${API_CONST.ACTIONS.DELETE_ITEM+id}`,this.token).subscribe(data=>{
      location.reload();
    },
    error=>{
    })
  }

  editItem(event:any){
    let id = event.srcElement.outerText.split(" ")[1];
    console.log(id);
    localStorage.setItem("editedUserId",id);
    this.router.navigateByUrl("/editItem")
  }

}
