import { Component, input } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Point } from '../../services/point';

@Component({
  selector: 'app-results-table',
  imports: [CommonModule, DatePipe],
  templateUrl: './results-table.html',
  styleUrl: './results-table.css',
})
export class ResultsTable {
  points = input.required<Point[]>();
}
