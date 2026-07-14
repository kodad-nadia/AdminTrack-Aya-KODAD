import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { TypeDemarche } from '../../core/models';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
})
export class AdminComponent implements OnInit {
  types: TypeDemarche[] = [];
  nom = '';
  organisme = '';
  delaiLegalJours = 30;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.charger();
  }

  charger(): void {
    this.http.get<TypeDemarche[]>(`${environment.apiUrl}/admin/types-demarches`)
      .subscribe((data) => (this.types = data));
  }

  ajouter(): void {
    this.http.post(`${environment.apiUrl}/admin/types-demarches`, {
      nom: this.nom, organisme: this.organisme, delaiLegalJours: this.delaiLegalJours,
    }).subscribe(() => {
      this.nom = ''; this.organisme = ''; this.delaiLegalJours = 30;
      this.charger();
    });
  }
}
