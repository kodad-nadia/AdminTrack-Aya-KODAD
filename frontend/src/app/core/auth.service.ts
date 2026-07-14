import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthResponse } from './models';

@Injectable({ providedIn: 'root' })
export class AuthService {

  // Etat d'authentification exposé de façon réactive (Angular signals)
  currentUser = signal<AuthResponse | null>(this.loadFromSession());

  constructor(private http: HttpClient) {}

  private loadFromSession(): AuthResponse | null {
    const raw = sessionStorage.getItem('admintrack_auth');
    return raw ? JSON.parse(raw) : null;
  }

  login(email: string, motDePasse: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, { email, motDePasse })
      .pipe(tap((res) => this.persist(res)));
  }

  register(nom: string, email: string, motDePasse: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/register`, { nom, email, motDePasse })
      .pipe(tap((res) => this.persist(res)));
  }

  logout(): void {
    sessionStorage.removeItem('admintrack_auth');
    this.currentUser.set(null);
  }

  getToken(): string | null {
    return this.currentUser()?.token ?? null;
  }

  isAdmin(): boolean {
    return this.currentUser()?.role === 'ADMIN';
  }

  private persist(res: AuthResponse): void {
    sessionStorage.setItem('admintrack_auth', JSON.stringify(res));
    this.currentUser.set(res);
  }
}
