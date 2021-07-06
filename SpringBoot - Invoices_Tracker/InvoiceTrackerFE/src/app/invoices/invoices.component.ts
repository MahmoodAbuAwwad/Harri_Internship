import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpService } from 'src/shared/http.service';
import { Invoice } from 'src/shared/models/invoice.model';
import { InvoiceResponse } from 'src/shared/models/InvoiceResponse.interface';
import { Users } from 'src/shared/models/user.interface';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-invoices',
  templateUrl: './invoices.component.html',
  styleUrls: ['./invoices.component.css']
})
export class InvoicesComponent implements OnInit {

  constructor(private httpService: HttpService, private router: Router) { }
  token:any
  allInvoices:Observable<InvoiceResponse[]> | undefined
  allInvoicesList: InvoiceResponse[] = [] 
   allInvoicesListFiltered: InvoiceResponse[] = []

  disabledNext:boolean=false
  userRole:any=""
  auditor:boolean=true
  isUser:boolean=false
  id:any
  length: number = 0

  paginator:number=0;

  ngOnInit(): void {
      this.userRole=localStorage.getItem('role')
      this.id=localStorage.getItem('id')

      if(this.userRole=="AUDITOR"){
        this.auditor=false
      }
      if(this.userRole=="USER"){
        this.isUser=true;
      }
      this.paginator=0;
      // this.isUser ?this.allInvoices = this.getAllInvoicesofUser(this.token,this.id) : this.allInvoices = this.getAllInvoices(this.token);
      this.token = localStorage.getItem("token")
      console.log(this.isUser);
      
      if(this.isUser){
        this.getUserData(this.paginator,10,"invoiceDate");
      }
      else{
        this.getData(this.paginator,10,"invoiceDate")
      }

   }


  getUserData(pageNo:number,pageSize:number,sortBy:string) {
    this.allInvoices = this.getAllInvoicesOfUser(this.token,pageNo,pageSize,sortBy);
      this.allInvoices.subscribe((data:any) =>{
        console.log(data)
        this.length=0;
        for(let i=0; i<data.length;i++){

          if(data[i].user.id== parseInt(this.id)){
            this.allInvoicesListFiltered.push(data[i])
          }
          this.allInvoicesList.push(data[i])
          this.length++;
        }
      })
      console.log((this.allInvoicesList));
  }
  getData(pageNo:number,pageSize:number,sortBy:string) {
    this.allInvoices = this.getAllInvoices(this.token,pageNo,pageSize,sortBy);
      this.allInvoices.subscribe((data:any) =>{
        console.log(data)
        this.length=0;
        for(let i=0; i<data.length;i++){

          if(data[i].user.id== parseInt(this.id)){
            this.allInvoicesListFiltered.push(data[i])
          }
          this.allInvoicesList.push(data[i])
          this.length++;
        }
      })
      console.log((this.allInvoicesList));
  }
  

    getAllInvoices(token:string,pageNo:number,pageSize:number,sortBy:string):Observable<InvoiceResponse[]>{
      console.log(`${API_CONST.ACTIONS.GET_INVOICES}`);
      return  this.httpService.getWithTokenAndParams(`${API_CONST.ACTIONS.GET_INVOICES}`, token,sortBy,pageNo,pageSize);
    }

    getAllInvoicesOfUser(token:string,pageNo:number,pageSize:number,sortBy:string):Observable<InvoiceResponse[]>{
      console.log(`${API_CONST.ACTIONS.GET_INVOICES+"/"+this.id}`);
      return  this.httpService.getWithTokenAndParams(`${API_CONST.ACTIONS.GET_INVOICES+"/"+this.id}`,  token,sortBy,pageNo,pageSize);
    }
  


  navigate(event:any){
    this.router.navigateByUrl("/newInvoice")
  }

  getItemsPaginated(){
    if(this.isUser){
      this.getUserData(this.paginator,10,"invoiceDate");
    }
    else{
      this.getData(this.paginator,10,"invoiceDate");
    }
  }
  
  getItemsNextPaginated(){
    this.allInvoicesList=[]
    this.paginator++;
    this.getItemsPaginated();
  }
  getItemsPreviousPaginated(){
    this.allInvoicesList=[]
    this.paginator--;
    this.getItemsPaginated();
  }

}
