import { Routes } from '@angular/router';
import { StartPage } from './components/start-page/start-page';
import { MainPage } from './components/main-page/main-page';
import { authGuard } from './guards/auth-guard';
import { LoginForm } from './components/login-form/login-form';
import { RegForm } from './components/reg-form/reg-form';

export const routes: Routes = [
  { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
  {
    path: 'auth',
    component: StartPage,
    children: [
      { path: 'reg', component: RegForm },
      { path: 'login', component: LoginForm },
      { path: '**', redirectTo: 'login' },
    ],
  },
  { path: 'main', component: MainPage, canActivate: [authGuard] },
  { path: '**', redirectTo: '/auth/login' },
];
