import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  showUsers:boolean = false;
  showItems:boolean=false;
  showInvoices:boolean=false;
  showNewUser:boolean=false;
  showNewItem:boolean=false;
  showNewInvoice:boolean=false;
  constructor() { }

  ngOnInit(): void {
    this.showInvoices=true;
  }

  

}
