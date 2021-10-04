import { Component } from '@angular/core';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styles: [
    '.background {background:white; color: black}',
    'li a { color: black}',
    'ul.nav a:hover { color: #fffccc  }'
  ]
})
export class HeaderComponent {
  constructor() {}

  }
