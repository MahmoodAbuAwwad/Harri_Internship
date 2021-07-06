import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpService } from 'src/shared/http.service';
import { Invoice } from 'src/shared/models/invoice.model';
import { InvoiceResponse } from 'src/shared/models/InvoiceResponse.interface';
import { InvoiceLogsResponse } from 'src/shared/models/invoice_logs.model';
import { API_CONST } from 'src/shared/shared.constants';

@Component({
  selector: 'app-preview-invoice',
  templateUrl: './preview-invoice.component.html',
  styleUrls: ['./preview-invoice.component.css']
})
export class PreviewInvoiceComponent implements OnInit {

  constructor(private httpService:HttpService, router:Router) { }
  token:any
  preveiwedInvoiceId:any
  invoicePreveiw:InvoiceResponse[]=[]
  invoicePreveiwLogs:InvoiceLogsResponse[]=[]
  
  ngOnInit(): void {
    this.invoicePreveiw=[];
    this.invoicePreveiwLogs=[];
    this.token = localStorage.getItem("token")
    this.preveiwedInvoiceId=localStorage.getItem("previewedInvoice");
    this.getInvoice()
    this.getInvoiceLogs()

  }

  getInvoice(){
    this.invoicePreveiw=[];
    console.log(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE+this.preveiwedInvoiceId}`);
    this.httpService.getWithToken(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE+parseInt(this.preveiwedInvoiceId)}`,this.token).subscribe((data:any)=>{
      this.invoicePreveiw?.push(data);
    })
  }

  getInvoiceLogs(){
    console.log(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE_LOGS+this.preveiwedInvoiceId}`);
    this.httpService.getWithToken(`${API_CONST.ACTIONS.PREVIEW_GET_INVOICE_LOGS+this.preveiwedInvoiceId}`,this.token).subscribe((data:any)=>{
      for(let i=0;i<data.length;i++){
        this.invoicePreveiwLogs.push(data[i]);
      }
      console.log(this.invoicePreveiwLogs);
    })
  }
}
