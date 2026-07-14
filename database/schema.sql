-- =========================================================
-- AdminTrack - Schema relationnel PostgreSQL
-- =========================================================

CREATE TABLE IF NOT EXISTS utilisateur (
    id                  BIGSERIAL PRIMARY KEY,
    nom                 VARCHAR(100) NOT NULL,
    email               VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe        VARCHAR(255) NOT NULL,
    role                VARCHAR(20)  NOT NULL DEFAULT 'USER',
    date_creation       TIMESTAMP DEFAULT now(),
    tentatives_echouees INT DEFAULT 0,
    compte_verrouille   BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS type_demarche (
    id                BIGSERIAL PRIMARY KEY,
    nom               VARCHAR(150) NOT NULL,
    organisme         VARCHAR(150),
    delai_legal_jours INT NOT NULL,
    description       VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS dossier (
    id               BIGSERIAL PRIMARY KEY,
    titre            VARCHAR(150) NOT NULL,
    statut           VARCHAR(30)  NOT NULL DEFAULT 'EN_COURS',
    date_creation    TIMESTAMP DEFAULT now(),
    utilisateur_id   BIGINT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
    type_demarche_id BIGINT NOT NULL REFERENCES type_demarche(id)
);

CREATE TABLE IF NOT EXISTS echeance (
    id           BIGSERIAL PRIMARY KEY,
    date_limite  DATE NOT NULL,
    depassee     BOOLEAN DEFAULT false,
    dossier_id   BIGINT NOT NULL UNIQUE REFERENCES dossier(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS document (
    id           BIGSERIAL PRIMARY KEY,
    nom_fichier  VARCHAR(255) NOT NULL,
    url_stockage VARCHAR(500) NOT NULL,
    date_ajout   TIMESTAMP DEFAULT now(),
    dossier_id   BIGINT NOT NULL REFERENCES dossier(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notification (
    id           BIGSERIAL PRIMARY KEY,
    canal        VARCHAR(20) NOT NULL DEFAULT 'EMAIL',
    date_envoi   TIMESTAMP DEFAULT now(),
    message      VARCHAR(1000),
    echeance_id  BIGINT NOT NULL REFERENCES echeance(id) ON DELETE CASCADE
);

-- Index utiles pour les requêtes les plus fréquentes
CREATE INDEX IF NOT EXISTS idx_dossier_utilisateur ON dossier(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_echeance_date_limite ON echeance(date_limite);
CREATE INDEX IF NOT EXISTS idx_document_dossier ON document(dossier_id);

-- Jeu de données initial (types de démarches)
INSERT INTO type_demarche (nom, organisme, delai_legal_jours, description) VALUES
    ('Renouvellement de titre de séjour', 'Préfecture', 120, 'Délai légal indicatif de traitement par la préfecture'),
    ('Renouvellement de passeport (consulat)', 'Consulat', 60, 'Délai indicatif variable selon le consulat'),
    ('Dossier CAF - demande de prestation', 'CAF', 30, 'Délai légal de réponse de la CAF'),
    ('Inscription France Travail', 'France Travail', 15, 'Délai indicatif de traitement du dossier'),
    ('Demande de naturalisation', 'Préfecture', 540, 'Délai légal maximal (18 mois) fixé par le CRPA')
ON CONFLICT DO NOTHING;
