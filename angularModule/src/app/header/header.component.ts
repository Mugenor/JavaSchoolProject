import { Component, OnInit } from '@angular/core';
import {Meta} from '@angular/platform-browser';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public username: string;
  public showMenu = false;
  constructor(private meta: Meta,
              private httpClient: HttpClient) { }

  ngOnInit() {
    const usernameMetaTag: HTMLMetaElement = this.meta.getTag('name="username"');
    this.username = usernameMetaTag.content === null ? 'You' : usernameMetaTag.content;
  }

  logout() {
    this.httpClient.post('logout', null).subscribe(null, (response) => {
      window.location.href = response.url;
    });
  }



}
