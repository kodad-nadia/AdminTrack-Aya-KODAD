import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DossierFormComponent } from './pages/dossier-form/dossier-form.component';
import { DossierDetailComponent } from './pages/dossier-detail/dossier-detail.component';
import { AdminComponent } from './pages/admin/admin.component';
import { authGuard } from './core/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
  { path: 'dossiers/nouveau', component: DossierFormComponent, canActivate: [authGuard] },
  { path: 'dossiers/:id', component: DossierDetailComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [authGuard] },
];
