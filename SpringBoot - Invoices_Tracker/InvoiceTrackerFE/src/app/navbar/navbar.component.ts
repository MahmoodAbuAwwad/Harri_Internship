import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor() { }
  userRole: any
  showUsersOnly:boolean=true
  showAll:boolean=false
  ngOnInit(): void {
    this.userRole = localStorage.getItem('role');
    if(this.userRole==="SUPERUSER"){
      this.showAll=true
    }

  }

  // must hide users and items from user 
  //must hide items and users from auditor too

}
