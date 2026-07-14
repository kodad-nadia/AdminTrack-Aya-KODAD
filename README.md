# AdminTrack

Assistant intelligent de suivi des démarches administratives — projet de fin d'études
(Titre Professionnel CDA / Mémoire ESTIAM).

Ce dépôt contient le code source complet du MVP (fonctionnalités "Must" du backlog) :
authentification JWT, création et suivi de dossiers, calcul automatique des échéances
légales, module d'administration des types de démarches.



## Structure du dépôt

```
admintrack/
├── backend/          Spring Boot 3 (Java 21) — API REST sécurisée par JWT
├── frontend/          Angular 18 (standalone components) — SPA
├── database/          schema.sql — schéma relationnel PostgreSQL + jeu de données
├── docker/             docker-compose.yml + Dockerfiles (backend, frontend)
└── README.md
```

