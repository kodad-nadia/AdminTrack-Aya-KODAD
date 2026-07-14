export interface AuthResponse {
  token: string;
  email: string;
  role: 'USER' | 'ADMIN';
}

export interface TypeDemarche {
  id: number;
  nom: string;
  organisme: string;
  delaiLegalJours: number;
  description?: string;
}

export interface Dossier {
  id: number;
  titre: string;
  statut: 'EN_COURS' | 'A_JOUR' | 'ECHEANCE_PROCHE' | 'DEPASSE' | 'CLOTURE';
  dateCreation: string;
  typeDemarche: string;
  echeanceDateLimite: string | null;
  echeanceDepassee: boolean;
  nombreDocuments: number;
}
