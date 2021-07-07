import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Invoice } from 'src/shared/models/invoice.model';
import { InvoiceResponse } from 'src/shared/models/InvoiceResponse.interface';
import { InvoiceLogsResponse } from 'src/shared/models/invoice_logs.model';
import { Items } from 'src/shared/models/item.interface';
import { API_CONST, ATTACHMENT_TYPE, INVOICE_TYPE, INVOICE_TYPE_E } from 'src/shared/shared.constants';

@Component({
  selector: 'app-edit-invoice',
  templateUrl: './edit-invoice.component.html',
  styleUrls: ['./edit-invoice.component.css']
})
export class EditInvoiceComponent implements OnInit {
  
  invoiceTotal: number=0;
  invoiceType:string="";
  fileType:string="";
  invoiceDate: Date = new Date();
  filePath:string=""
  constructor(private formBuilder: FormBuilder,private httpService:HttpService,private router:Router) { }
  token:any
  preveiwedInvoiceId:any
  invoicePreveiw:InvoiceResponse[]=[]
  invoicePreveiwLogs:InvoiceLogsResponse[]=[]
  allItems:Observable<Items[]> | undefined
  allItemsList: Items[] = []

  allInvoiceItems:Observable<Items[]> | undefined
  allInvoiceItemsList: Items[] = []

  updateAttachment: boolean = true

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
  file!: File; 
  note:string=""
  options = [
    { name: "Paid", value: INVOICE_TYPE_E.PAID },
    { name: "Not Paid", value: INVOICE_TYPE_E.NOT_PAID },
    { name: "Half Paid", value: INVOICE_TYPE_E.HALF_PAID },
  ]

  isNone:boolean = true
  isImage:boolean = false
  isFile:boolean = false

  updateAttch(event:any){
    this.updateAttachment=!this.updateAttachment;
  }

  async ngOnInit(): Promise<void> {
    this.selectedItems=[]
    this.invoicePreveiw=[];
    this.token = localStorage.getItem("token")
    this.preveiwedInvoiceId = localStorage.getItem("editedInvoiceId")
    this.allItems = this.getAllItems(this.token);

    this.getUserInformation(this.token)
    this.allItems.subscribe(data =>{
      for(let i=0; i<data.length;i++){
        this.allItemsList.push(data[i])
      }
    })    
    this.getInvoice()
    await this.delay(500);
    switch(this.fileType){
      case "None" : this.isNone = true;
      break;
      case "Image" : this.isImage = true;
      break;
      case "File" : this.isFile = true;
      break;
    }
    this.total = this.invoiceTotal;    
    switch(this.invoiceType){
      case INVOICE_TYPE_E.PAID : this.selectedOption = 0;
      break;
      case INVOICE_TYPE_E.NOT_PAID : this.selectedOption = 1;
      break;
      case INVOICE_TYPE_E.HALF_PAID: this.selectedOption = 2;
      break;
    }    

    this.allInvoiceItems = this.getInvoiceItems(this.token);
    this.allInvoiceItems.subscribe(data =>{
      for(let i=0; i<data.length;i++){
        this.allInvoiceItemsList.push(data[i])
      }
    }) 
    this.finalSelectedItems = this.allInvoiceItemsList;
    this.selectedFileType = this.fileType;
   }
  
   isSelectedItem(id?:number){
     for(let i =0 ; i<this.allInvoiceItemsList.length;i++){
       if(this.allInvoiceItemsList[i].id == id){
         return true;
       }
     }
     return false;
   }
    getInvoiceItems(token:string):Observable<Items[]>{
      return  this.httpService.getWithToken(`${API_CONST.ACTIONS.GET_INVOICE_ITEMS+parseInt(this.preveiwedInvoiceId)}`, token);
    }
   getInvoice(){
      this.invoicePreveiw=[];
      console.log(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE+this.preveiwedInvoiceId}`);
      this.httpService.getWithToken(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE+parseInt(this.preveiwedInvoiceId)}`,this.token).subscribe((data:any)=>{ 
        console.log(data);
        
        this.filePath =data.filePath;
        this.fileType = data.fileType;
        this.invoiceTotal = data.invoiceTotal;
        this.invoiceDate = new Date()
        this.invoiceType = data.invoiceType
        this.invoicePreveiw.push(data);
      })     
    }

     delay(ms: number) {
      return new Promise( resolve => setTimeout(resolve, ms) );
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

    onItemChange(value:any ){ //this for selecting attachment type
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
    console.log(this.selectedItems);
    console.log(this.finalSelectedItems);
    
    this.total=0;
    for(let i=0;i<this.finalSelectedItems.length;i++){
      this.total=this.total+parseFloat(this.finalSelectedItems[i].price);
    }

  }
  

  uploadFile(event :any){
    this.file = event.target.value; 
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
      let invoice =  new Invoice(this.total,this.selectedOption,this.user,this.selectedFileType,this.file,this.finalSelectedItems,parseInt(this.preveiwedInvoiceId),this.note)
      console.log(JSON.stringify(invoice));
      console.log(this.file);
      
      let formData = new FormData();
      formData.append('file', this.file);
      formData.append('json', JSON.stringify(invoice));
      
      this.httpService.putWithTokenNoContent(`${API_CONST.ACTIONS.EDIT_INVOICE+this.preveiwedInvoiceId}`, formData, this.token).toPromise().then((response) => {
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
