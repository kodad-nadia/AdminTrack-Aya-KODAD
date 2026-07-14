import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { DossierService } from '../../core/dossier.service';
import { Dossier } from '../../core/models';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  dossiers: Dossier[] = [];

  constructor(private dossierService: DossierService, public auth: AuthService) {}

  ngOnInit(): void {
    this.dossierService.listerDossiers().subscribe((data) => (this.dossiers = data));
  }

  badgeClass(statut: string): string {
    switch (statut) {
      case 'DEPASSE': return 'badge badge-depasse';
      case 'ECHEANCE_PROCHE': return 'badge badge-proche';
      default: return 'badge badge-ok';
    }
  }
}
