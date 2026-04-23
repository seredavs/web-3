import { Component, OnInit, output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-point-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './point-form.html',
  styleUrl: './point-form.css',
})
export class PointForm implements OnInit {
  pointSubmit = output<{ x: number; y: number; r: number }>();
  radiusChange = output<number>();

  pointForm: FormGroup;
  xOptions = ['-4', '-3', '-2', '-1', '0', '1', '2', '3', '4'];
  rOptions = ['1', '2', '3', '4'];

  constructor() {
    const fb = inject(FormBuilder);

    this.pointForm = fb.group({
      x: ['0', Validators.required],
      y: [
        '',
        [
          Validators.required,
          Validators.min(-3),
          Validators.max(5),
          Validators.pattern(/^-?\d+(\.\d+)?$/),
        ],
      ],
      r: ['1', Validators.required],
    });
  }

  ngOnInit() {
    this.radiusChange.emit(Number(this.pointForm.get('r')?.value));

    this.pointForm.get('r')?.valueChanges.subscribe((val) => {
      this.radiusChange.emit(Number(val));
    });
  }

  onSubmit() {
    if (this.pointForm.valid) {
      const { x, y, r } = this.pointForm.value;
      this.pointSubmit.emit({
        x: Number(x),
        y: Number(y),
        r: Number(r),
      });
    }
  }
}
