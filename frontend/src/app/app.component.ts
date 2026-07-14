import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from './core/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <nav style="background:#1f4e79;color:#fff;padding:12px 24px;display:flex;justify-content:space-between;">
      <a routerLink="/dashboard" style="color:#fff;font-weight:bold;text-decoration:none;">AdminTrack</a>
      <div *ngIf="auth.currentUser() as user">
        <span style="margin-right:16px;">{{ user.email }}</span>
        <button (click)="auth.logout()" class="btn">Déconnexion</button>
      </div>
    </nav>
    <router-outlet></router-outlet>
  `,
})
export class AppComponent {
  constructor(public auth: AuthService) {}
}
