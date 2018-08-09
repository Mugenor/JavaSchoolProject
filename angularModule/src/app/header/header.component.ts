import { Component, OnInit } from '@angular/core';
import {Meta} from "@angular/platform-browser";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public username: string;
  public showMenu = false;
  constructor(private meta: Meta) { }

  ngOnInit() {
    let usernameMetaTag: HTMLMetaElement = this.meta.getTag('name="username"');
    console.log(usernameMetaTag.content);
    this.username = usernameMetaTag.content === null ? 'You' : usernameMetaTag.content;
  }



}
