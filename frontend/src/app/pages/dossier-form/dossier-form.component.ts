import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DossierService } from '../../core/dossier.service';
import { TypeDemarche } from '../../core/models';

@Component({
  selector: 'app-dossier-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dossier-form.component.html',
})
export class DossierFormComponent implements OnInit {
  titre = '';
  typeDemarcheId: number | null = null;
  types: TypeDemarche[] = [];
  erreur = '';

  constructor(private dossierService: DossierService, private router: Router) {}

  ngOnInit(): void {
    this.dossierService.listerTypesDemarches().subscribe((data) => (this.types = data));
  }

  submit(): void {
    if (!this.typeDemarcheId) {
      this.erreur = 'Veuillez sélectionner un type de démarche.';
      return;
    }
    this.dossierService.creerDossier(this.titre, this.typeDemarcheId).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => this.erreur = err.error?.erreur ?? 'Erreur lors de la création.',
    });
  }
}
