import {
  Component,
  ElementRef,
  input,
  OnChanges,
  OnInit,
  output,
  SimpleChanges,
  viewChild,
} from '@angular/core';
import { Point } from '../../services/point';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.html',
  styleUrl: './graph.css',
})
export class Graph implements OnInit, OnChanges {
  canvasRef = viewChild<ElementRef<HTMLCanvasElement>>('canvas');
  r = input.required<number>();
  points = input.required<Point[]>();
  canvasClick = output<{ x: number; y: number }>();

  private canvas!: HTMLCanvasElement;
  private ctx!: CanvasRenderingContext2D;
  private width!: number;
  private height!: number;
  private cx!: number;
  private cy!: number;
  private scale = 40;

  ngOnInit() {
    this.canvas = this.canvasRef()!.nativeElement;
    this.ctx = this.canvas.getContext('2d')!;
    this.width = this.canvas.width;
    this.height = this.canvas.height;
    this.cx = this.width / 2;
    this.cy = this.height / 2;
    this.draw();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['r'] || changes['points']) {
      this.draw();
    }
  }

  draw() {
    if (!this.ctx) return;

    this.ctx.clearRect(0, 0, this.width, this.height);

    this.drawArea();
    this.drawAxes();
    this.drawPoints();
  }

  drawAxes() {
    const ctx = this.ctx;
    ctx.strokeStyle = 'black';
    ctx.lineWidth = 1;
    ctx.font = '12px sans-serif';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    ctx.beginPath();
    ctx.moveTo(0, this.cy);
    ctx.lineTo(this.width, this.cy);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(this.cx, 0);
    ctx.lineTo(this.cx, this.height);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(this.width - 5, this.cy - 3);
    ctx.lineTo(this.width, this.cy);
    ctx.lineTo(this.width - 5, this.cy + 3);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(this.cx - 3, 5);
    ctx.lineTo(this.cx, 0);
    ctx.lineTo(this.cx + 3, 5);
    ctx.stroke();

    ctx.fillText('X', this.width - 10, this.cy - 10);
    ctx.fillText('Y', this.cx + 10, 10);

    const ticks = [-4, -3, -2, -1, 1, 2, 3, 4];
    for (const t of ticks) {
      this.drawTick(this.cx + t * this.scale, this.cy, t.toString());
      this.drawTick(this.cx, this.cy - t * this.scale, t.toString());
    }
  }

  drawTick(x: number, y: number, label: string) {
    const ctx = this.ctx;
    const tickSize = 5;
    ctx.beginPath();
    if (Math.abs(y - this.cy) < 0.1) {
      ctx.moveTo(x, y - tickSize);
      ctx.lineTo(x, y + tickSize);
      ctx.fillText(label, x, y + 15);
    } else {
      ctx.moveTo(x - tickSize, y);
      ctx.lineTo(x + tickSize, y);
      ctx.fillText(label, x + 20, y);
    }
    ctx.stroke();
  }

  drawArea() {
    if (this.r() <= 0) return;

    const ctx = this.ctx;
    const rPixels = this.r() * this.scale;
    const halfRPixels = (this.r() / 2) * this.scale;

    ctx.fillStyle = '#42a5f5';
    ctx.globalAlpha = 0.5;

    ctx.beginPath();
    ctx.moveTo(this.cx, this.cy);
    ctx.arc(this.cx, this.cy, rPixels, Math.PI, 1.5 * Math.PI);
    ctx.lineTo(this.cx, this.cy);
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(this.cx, this.cy);
    ctx.lineTo(this.cx + halfRPixels, this.cy);
    ctx.lineTo(this.cx, this.cy - rPixels);
    ctx.fill();

    ctx.fillRect(this.cx, this.cy, rPixels, rPixels);

    ctx.globalAlpha = 1.0;
  }

  drawPoints() {
    this.points().forEach((p) => {
      const screenX = this.cx + p.x * this.scale;
      const screenY = this.cy - p.y * this.scale;

      this.ctx.beginPath();
      this.ctx.arc(screenX, screenY, 3, 0, 2 * Math.PI);
      this.ctx.fillStyle = p.hit ? 'green' : 'red';
      this.ctx.fill();
    });
  }

  onClick(event: MouseEvent) {
    const rect = this.canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const mathX = (x - this.cx) / this.scale;
    const mathY = (this.cy - y) / this.scale;

    this.canvasClick.emit({ x: mathX, y: mathY });
  }
}
