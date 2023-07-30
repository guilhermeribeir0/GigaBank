import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  colorIconEmail: string = '';
  colorIconPassword: string = '';

  constructor() { }

  ngOnInit(): void {

  }

  activedColorIcon(icon: string, value: boolean) {
    if (value == true) {
      if (icon === 'email') {
        this.colorIconEmail = 'primary';
        this.colorIconPassword = 'black';
      } else {
        this.colorIconEmail = 'black';
        this.colorIconPassword = 'primary';
      }
    } else {
      this.colorIconEmail = 'black';
      this.colorIconPassword = 'black';
    }
  }

}
