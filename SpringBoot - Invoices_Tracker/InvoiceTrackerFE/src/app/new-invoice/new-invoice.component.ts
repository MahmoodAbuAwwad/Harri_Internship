import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Invoice } from 'src/shared/models/invoice.model';
import { Items } from 'src/shared/models/item.interface';
import { API_CONST, ATTACHMENT_TYPE, INVOICE_TYPE } from 'src/shared/shared.constants';

@Component({
  selector: 'app-new-invoice',
  templateUrl: './new-invoice.component.html',
  styleUrls: ['./new-invoice.component.css']
})
export class NewInvoiceComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private httpService:HttpService,private router:Router) { }
  token:any
  allItems:Observable<Items[]> | undefined
  allItemsList: Items[] = []
  selectedOption: any =0 ;
  selectedAttachmentType: any =0 ;
  selectedFileType:string = ATTACHMENT_TYPE.NONE
  errorFlag:boolean=false
  errorMessage:string=""
  total:number =0
  user:any 
  selectedItems:string[]=[]
  finalSelectedItems:Items[]=[]
  uploadForm: any | FormGroup;  
  file: File | null = null;
  options = [
    { name: "Paid", value: INVOICE_TYPE.PAID },
    { name: "Not Paid", value: INVOICE_TYPE.NOT_PAID },
    { name: "Half Paid", value: INVOICE_TYPE.HALF_PAID },
  ]

  ngOnInit(): void {
    this.token = localStorage.getItem("token")
    this.allItems = this.getAllItems(this.token);
    this.getUserInformation(this.token)
    this.allItems.subscribe(data =>{
      for(let i=0; i<data.length;i++){
        this.allItemsList.push(data[i])
      }
    })
   }

    getAllItems(token:string):Observable<Items[]>{
      return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_ITEMS}`, token);
    }
    selectType(event:any) {

      let fireboxFix = event.target || event.srcElement;
      let indexToFind = (<HTMLSelectElement>fireboxFix).value;
      let testArray = [];
      for (let row of this.options) {
         testArray.push(row.name)
      }
      this.selectedOption = testArray.indexOf(indexToFind);
      console.log(this.selectedOption)
   }

    onItemChange(value:any ){
      this.selectedFileType=value.target.value
      console.log(this.selectedFileType);
      
    }

  getUserInformation(token :string) {
    this.httpService.getWithToken(`${API_CONST.ACTIONS.LOGGED_IN}`,token).subscribe((user:any)=>{
      console.log(user);
      this.user=user;
  })}

  items(event:any){
    this.finalSelectedItems=[]
    for(let i=0;i<this.selectedItems.length;i++){
      for(let j=0;j<this.allItemsList.length;j++){
        if(this.selectedItems[i]===this.allItemsList[j].name){    
          this.finalSelectedItems.push(this.allItemsList[j])
        }
      }
    }
    this.total=0;
    for(let i=0;i<this.finalSelectedItems.length;i++){
      this.total=this.total+parseFloat(this.finalSelectedItems[i].price);

    }

  }

  uploadFile(event :any){
    this.file = event.target.files.item(0); 
    console.log(this.file); 
  }
  onSubmit(){
    this.errorFlag=false
    if(this.total==null || this.total==undefined ){
      this.errorMessage="Fill Total price please !"
      this.errorFlag=true
    }
    if(this.errorFlag === false){
      this.errorFlag=false
      let invoice =  new Invoice(this.total,this.selectedOption,this.user,this.selectedFileType,this.file!,this.finalSelectedItems)
      console.log(invoice);
      console.log(this.file)
      let form_data = new FormData();
      console.log(this.file)
      form_data.append('file', this.file! , this.file?.name);
      form_data.append('json', JSON.stringify(invoice));

      
      this.httpService.postWithTokenNoContent(`${API_CONST.ACTIONS.ADD_INVOICE}`, (form_data), this.token).toPromise().then((response) => {
        this.errorFlag=false;
        this.router.navigateByUrl("/invoices");

      }).catch((err: HttpErrorResponse) => {
        console.error( err.error);
        this.errorFlag=true;
        this.errorMessage=err.error;
      });
    }
  }
}
