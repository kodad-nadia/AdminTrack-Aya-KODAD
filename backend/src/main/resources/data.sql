-- Jeu de données initial : types de démarches avec leurs délais légaux de référence.
-- Ce script est exécuté au démarrage (Spring Boot data.sql) uniquement si la table est vide.
INSERT INTO type_demarche (nom, organisme, delai_legal_jours, description)
SELECT * FROM (VALUES
    ('Renouvellement de titre de séjour', 'Préfecture', 120, 'Délai légal indicatif de traitement par la préfecture'),
    ('Renouvellement de passeport (consulat)', 'Consulat', 60, 'Délai indicatif variable selon le consulat'),
    ('Dossier CAF - demande de prestation', 'CAF', 30, 'Délai légal de réponse de la CAF'),
    ('Inscription France Travail', 'France Travail', 15, 'Délai indicatif de traitement du dossier'),
    ('Demande de naturalisation', 'Préfecture', 540, 'Délai légal maximal (18 mois) fixé par le CRPA')
) AS v(nom, organisme, delai_legal_jours, description)
WHERE NOT EXISTS (SELECT 1 FROM type_demarche);
