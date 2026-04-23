import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Point {
  id?: number;
  x: number;
  y: number;
  r: number;
  hit: boolean;
  timestamp: string;
}

interface ApiResponse<T> {
  error: boolean;
  data: T;
}

interface PointDto {
  id: number;
  x: number;
  y: number;
  r: number;
  hit: boolean;
  date: string;
}

@Injectable({
  providedIn: 'root',
})
export class PointService {
  private http = inject(HttpClient);
  private points = signal<Point[]>([]);
  private readonly baseUrl = '/api/points';

  loadPoints() {
    this.http.get<ApiResponse<PointDto[]>>(this.baseUrl).subscribe({
      next: (res) => {
        if (!res.error) {
          this.points.set(
            res.data
              .map((p) => ({
                id: p.id,
                x: p.x,
                y: p.y,
                r: p.r,
                hit: p.hit,
                timestamp: p.date,
              }))
              .reverse(),
          );
        }
      },
      error: (err) => console.error('Failed to load points', err),
    });
  }

  get(r: number): Point[] {
    return this.points().filter((point) => point.r == r);
  }

  add(x: number, y: number, r: number): void {
    this.http.post<ApiResponse<PointDto>>(this.baseUrl, { x, y, r }).subscribe({
      next: (res) => {
        if (!res.error) {
          const p = res.data;
          const newPoint: Point = {
            id: p.id,
            x: p.x,
            y: p.y,
            r: p.r,
            hit: p.hit,
            timestamp: p.date,
          };
          this.points.update((points) => [newPoint, ...points]);
        } else {
          alert('Error adding point');
        }
      },
      error: (err) => {
        console.error('Failed to add point', err);
        alert('Failed to add point');
      },
    });
  }
}
