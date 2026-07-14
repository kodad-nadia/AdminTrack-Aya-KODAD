import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DossierService } from '../../core/dossier.service';
import { Dossier } from '../../core/models';

@Component({
  selector: 'app-dossier-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dossier-detail.component.html',
})
export class DossierDetailComponent implements OnInit {
  dossier: Dossier | null = null;

  constructor(private route: ActivatedRoute, private dossierService: DossierService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.dossierService.getDossier(id).subscribe((d) => (this.dossier = d));
  }

  genererCourrier(): void {
    // Appelle a terme un endpoint /api/dossiers/{id}/courrier qui genere un modele
    // de lettre de relance/saisine a partir des donnees du dossier (BF07).
    alert('Génération du courrier type à implémenter côté back-end (endpoint /api/dossiers/{id}/courrier).');
  }
}
