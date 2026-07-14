import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Dossier, TypeDemarche } from './models';

@Injectable({ providedIn: 'root' })
export class DossierService {

  constructor(private http: HttpClient) {}

  listerDossiers(): Observable<Dossier[]> {
    return this.http.get<Dossier[]>(`${environment.apiUrl}/dossiers`);
  }

  getDossier(id: number): Observable<Dossier> {
    return this.http.get<Dossier>(`${environment.apiUrl}/dossiers/${id}`);
  }

  creerDossier(titre: string, typeDemarcheId: number): Observable<Dossier> {
    return this.http.post<Dossier>(`${environment.apiUrl}/dossiers`, { titre, typeDemarcheId });
  }

  listerTypesDemarches(): Observable<TypeDemarche[]> {
    return this.http.get<TypeDemarche[]>(`${environment.apiUrl}/types-demarches`);
  }
}
