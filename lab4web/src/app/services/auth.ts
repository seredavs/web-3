import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

type AuthResponse = { error: false; data: { token: string } } | { error: true; data: string };

type MeResponse = { error: boolean; data: string };

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);
  private readonly baseUrl = '/api';

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  login(username: string, password: string): void {
    this.http.post<AuthResponse>(`${this.baseUrl}/login`, { username, password }).subscribe({
      next: (res) => {
        if (!res.error) {
          localStorage.setItem('token', res.data.token);
          localStorage.setItem('username', username);
          this.router.navigate(['/main']);
        } else {
          alert(res.data);
        }
      },
      error: (err) => {
        console.error('Login error:', err);
        alert('Login failed. Please try again.');
      },
    });
  }

  register(username: string, password: string): void {
    this.http.post<AuthResponse>(`${this.baseUrl}/register`, { username, password }).subscribe({
      next: (res) => {
        if (!res.error) {
          localStorage.setItem('token', res.data.token);
          localStorage.setItem('username', username);
          this.router.navigate(['/main']);
        } else {
          alert(res.data);
        }
      },
      error: (err) => {
        console.error('Registration error:', err);
        alert('Registration failed. Please try again.');
      },
    });
  }

  checkAuth(): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    this.http.get<MeResponse>(`${this.baseUrl}/me`).subscribe({
      next: (res) => {
        if (!res.error) {
          localStorage.setItem('username', res.data);
        } else {
          this.logout();
        }
      },
      error: () => this.logout(),
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.router.navigate(['auth', 'login']);
  }
}
