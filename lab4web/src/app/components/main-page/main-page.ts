import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Header } from '../header/header';
import { Graph } from '../graph/graph';
import { PointForm } from '../point-form/point-form';
import { ResultsTable } from '../results-table/results-table';
import { PointService } from '../../services/point';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-main-page',
  imports: [CommonModule, Header, Graph, PointForm, ResultsTable],
  templateUrl: './main-page.html',
  styleUrl: './main-page.css',
})
export class MainPage implements OnInit {
  auth = inject(AuthService);
  points = inject(PointService);
  r: number = 1;

  ngOnInit() {
    this.auth.checkAuth();
    this.points.loadPoints();
  }

  onPointSubmit(data: { x: number; y: number; r: number }) {
    this.points.add(data.x, data.y, data.r);
  }

  onCanvasClick(coords: { x: number; y: number }) {
    this.points.add(coords.x, coords.y, this.r);
  }

  onRadiusChange(r: number) {
    this.r = r;
  }
}
