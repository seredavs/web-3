import { Component } from '@angular/core';
import { Header } from '../header/header';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-start-page',
  imports: [Header, RouterOutlet],
  templateUrl: './start-page.html',
  styleUrl: './start-page.css',
})
export class StartPage {}
