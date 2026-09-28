# Tombo App

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen" alt="Spring Boot 3.5.3" />
  <img src="https://img.shields.io/badge/React-18-61dafb" alt="React 18" />
  <img src="https://img.shields.io/badge/Vite-5-646cff" alt="Vite 5" />
  <img src="https://img.shields.io/badge/PostgreSQL-15-336791" alt="PostgreSQL 15" />
  <img src="https://img.shields.io/badge/Docker-Compose-2496ed" alt="Docker Compose" />
</p>

Une plateforme full-stack de gestion et de publication d’annonces automobiles, conçue pour permettre à un utilisateur de :

- créer un compte et se connecter en toute sécurité,
- publier des annonces de véhicules,
- ajouter des images et des détails techniques,
- recevoir des alertes et des notifications,
- consulter un tableau de bord de statistiques,
- filtrer et parcourir les annonces facilement.

## Objectif du projet

Ce projet a été développé pour répondre à un besoin réel: simplifier l’expérience de publication et de recherche d’annonces automobiles en centralisant les opérations sur une application moderne et sécurisée.

L’objectif principal est de proposer une solution complète qui combine :

- une partie backend robuste et sécurisée,
- une interface frontend moderne et réactive,
- une architecture scalable pour la gestion des utilisateurs, annonces, alertes et notifications,
- un environnement préconfiguré avec Docker pour faciliter le déploiement.

## Principe du projet

Le projet suit le principe de la séparation des responsabilités :

- le backend gère la logique métier, l’authentification, la persistance et les services,
- le frontend s’occupe de l’expérience utilisateur et de l’interaction avec l’API,
- les données sont structurées et validées avant d’être stockées,
- les fichiers uploadés sont gérés avec une logique sécurisée pour éviter les erreurs de traitement.

En termes simples, le système est conçu pour être à la fois fonctionnel, modulaire et facile à faire évoluer.

## Fonctionnalités principales

### Authentification et sécurité
- Inscription utilisateur
- Connexion sécurisée
- JWT pour l’authentification
- Protection des routes sensibles
- Gestion CORS pour le frontend
- BCrypt pour le hashage des mots de passe

### Gestion des annonces
- Création d’annonces de véhicules
- Ajout d’images associées à une annonce
- Mise à jour des informations d’une annonce
- Suppression d’une annonce
- Récupération de toutes les annonces ou celles d’un utilisateur spécifique

### Recherche et filtrage
- Filtrage par marque, modèle, année, prix, kilométrage, ville, carburant et transmission
- Liste paginée des résultats
- Interface de recherche ergonomique pour les utilisateurs

### Alertes et notifications
- Création d’alertes de recherche
- Notification automatique lors de nouveaux événements
- Tableau de bord des statistiques utilisateur

### Dashboard
- Nombre d’annonces
- Nombre d’alertes actives
- Nombre de notifications
- Graphiques de synthèse pour un suivi rapide

## Stack technique

### Backend
- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JJWT)
- PostgreSQL
- Lombok
- Jakarta Validation
- Twilio SDK

### Frontend
- React 18
- Vite
- TypeScript
- Redux Toolkit
- React Router
- Tailwind CSS
- Shadcn UI
- Recharts
- Axios

### Infrastructure
- Docker
- Docker Compose
- PostgreSQL containerisé
- Service proxy pour les données automobiles

## Architecture du projet

```text
Tombo-App/
├── Tomoghik/                  # Backend Spring Boot
│   ├── src/main/java/         # Code Java
│   ├── src/main/resources/    # Configurations et properties
│   └── pom.xml                # Dépendances Maven
├── tomboFrontend/             # Frontend React
│   ├── src/                   # Composants, pages, store
│   ├── package.json           # Dépendances npm
│   └── vite.config.ts        # Configuration Vite
├── car-proxy/                 # Proxy / service externe pour infos voiture
├── deployment/                # Déploiement Jenkins / Ansible
├── docker-compose.yml         # Orchestration des services
├── uploads/                   # Dossier de stockage des images
└── README.md
```

## Concepts implémentés

### 1. Architecture MVC / Service Layer
Le backend est organisé selon une logique claire :

- Contrôleurs : exposent les endpoints REST
- Services : contiennent la logique métier
- Repository : gère la persistance avec JPA
- Modèles : représentent les entités métier
- DTO : simplifient les échanges entre API et frontend

### 2. Sécurité applicative
La sécurisation du projet repose sur plusieurs bonnes pratiques :

- mots de passe hachés via BCrypt,
- authentification JWT,
- filtres personnalisés pour valider les tokens,
- autorisation des endpoints sensibles,
- configuration CORS pour sécuriser les échanges frontend/backend.

### 3. Gestion des fichiers
Les images de véhicules sont stockées localement dans le dossier uploads, avec :

- génération de noms uniques,
- création automatique du dossier si nécessaire,
- copie sécurisée des fichiers sur disque,
- association des images à l’annonce concernée.

### 4. Validation des données
Les entités utilisent des contraintes de validation (ex. année minimale, kilométrage minimal) pour éviter des données incohérentes.

### 5. Transactions et cohérence des données
Le service annonce est annoté avec `@Transactional`, ce qui aide à maintenir la cohérence des opérations sur les données et à éviter les incohérences lors des modifications.

### 6. API REST moderne
Les endpoints sont structurés de manière cohérente pour :

- créer des ressources,
- mettre à jour des ressources,
- supprimer des ressources,
- récupérer des listes et des détails,
- exposer des données en JSON.

## Bonnes pratiques mises en œuvre

### Backend
- séparation controller / service / repository,
- utilisation de DTO pour mieux contrôler les données exposées,
- validation des entrées,
- gestion des exceptions et messages d’erreur fonctionnels,
- configuration centralisée pour le système de sécurité,
- utilisation de Lombok pour réduire le code boilerplate,
- support de transactions pour les opérations critiques.

### Frontend
- composants réutilisables,
- gestion centralisée de l’état avec Redux Toolkit,
- navigation avec React Router,
- composants UI cohérents via Shadcn UI,
- utilisation de Tailwind pour une interface moderne et responsive,
- données dynamiques via API REST,
- filtrage côté client pour améliorer l’expérience utilisateur.

### Infrastructure
- conteneurisation avec Docker,
- orchestration via Docker Compose,
- variable d’environnement pour les configurations sensibles,
- environnement reproductible pour le déploiement.

## Prérequis

Avant de lancer le projet, assure-toi d’avoir installé :

- Java 17+
- Maven
- Node.js 18+
- npm ou bun
- Docker
- Docker Compose

## Lancement du projet

### 1. Cloner le dépôt

```bash
git clone https://github.com/<votre-utilisateur>/<votre-repo>.git
cd Tombo-App
```

### 2. Lancer tous les services avec Docker

```bash
docker-compose up --build
```

Cela démarrera :

- la base PostgreSQL,
- le backend Spring Boot,
- le frontend React,
- le service proxy pour les données automobiles.

### 3. Accès applicatif

- Frontend : http://localhost:8080
- Backend API : http://localhost:8082
- Base PostgreSQL : localhost:5432

## Variables d’environnement

Le backend utilise les variables suivantes :

```env
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
UPLOAD_DIR=
FRONTEND_URL=
```

## Exemple d’utilisation

Le projet permet de :

- créer un utilisateur,
- authentifier l’utilisateur avec JWT,
- publier une annonce avec description, prix, année, carburant, localisation,
- ajouter des photos du véhicule,
- filtrer les annonces selon les critères recherchés,
- recevoir des alertes et visualiser les statistiques dans le dashboard.

## Défis techniques relevés

Au cours du développement, plusieurs points importants ont été traités :

- sécurisation des flux API,
- gestion des fichiers uploadés,
- intégration backend/frontend,
- gestion CORS,
- cohérence de données entre entités liées,
- mise en place d’une architecture testable et évolutive.

## Conclusion

Tombo App est un projet qui montre une vraie capacité à concevoir et développer une application complète avec une architecture saine, des principes de sécurité solides, une interface moderne et une logique métier cohérente.

---
