import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-reg-form',
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './reg-form.html',
  styleUrl: './reg-form.css',
})
export class RegForm {
  private auth = inject(AuthService);

  regForm: FormGroup;

  constructor() {
    const fb = inject(FormBuilder);

    this.regForm = fb.group({
      login: ['', [Validators.required, Validators.pattern(/^\w+$/)]],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.regForm.valid) {
      const { login, password } = this.regForm.value;
      this.auth.register(login, password);
    }
  }
}
