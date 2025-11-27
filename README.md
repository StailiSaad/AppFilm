<<<<<<< HEAD
# 🎬 Application Film - Application de Streaming de Films

Une application Android moderne de streaming de films développée en Kotlin qui permet aux utilisateurs de parcourir des films, gérer leurs favoris et souscrire à des abonnements VIP premium.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Picasso](https://img.shields.io/badge/Picasso-FF6D00?style=for-the-badge)

## 📱 Fonctionnalités

### 🎯 Fonctionnalités Principales
- **Navigation des Films** : Parcourir les films across multiples catégories (Tendances, Populaires, Nouvelles Sorties, Action, Comédie)
- **Détails des Films** : Vue détaillée avec descriptions, notations et métadonnées
- **Gestion des Favoris** : Ajouter/supprimer des films aux favoris personnels avec glisser-supprimer
- **Authentification Utilisateur** : Système de connexion simple
- **Abonnement VIP** : Abonnement premium à trois niveaux avec flux de paiement sécurisé

### 🎨 Expérience Utilisateur
- **Thème Sombre** : Belle interface sombre avec accents dorés
- **Défilement Horizontal** : Navigation fluide par catégories
- **Chargement d'Images** : Chargement rapide d'images avec Picasso
- **Design Responsive** : Optimisé pour différentes tailles d'écran
- **Gestes de Glissement** : Glisser-supprimer intuitif dans les favoris

### 💳 Système de Paiement
- **Niveaux VIP** :
    - Niveau 1 : 9,99€/mois - Fonctionnalités de base
    - Niveau 2 : 19,99€/mois - Fonctionnalités premium
    - Niveau 3 : 29,99€/mois - Fonctionnalités ultimes
- **Méthodes de Paiement** : Support Visa, MasterCard, PayPal
- **Validation Sécurisée** : Validation complète des entrées

## 🏗️ Architecture

📱 Structure de l'Application
├── 🏠 MainActivity - Écran d'accueil avec catégories de films
├── 🎞️ FilmDetailsActivity - Vue détaillée des films
├── ⭐ FavoritesActivity - Gestion des favoris
├── 🔐 LoginActivity - Authentification utilisateur
├── 💳 PaymentActivity - Abonnement VIP
├── ✅ PaymentSuccessActivity - Confirmation de paiement
└── 🛠️ Classes de Support
├── Film - Modèle de données
├── FilmAdapter - Adaptateur de liste
├── FavoritesManager - Wrapper SharedPreferences
└── FavoritesAdapter - Adaptateur RecyclerView

## 📸 Documentation Technique - Screenshot Dokka
![Documentation Dokka](app/src/screenshots/dokka.PNG)
Cette capture montre la documentation technique générée par Dokka, incluant toutes les classes, fonctions et commentaires KDoc du projet.

## 📸 Captures d'Écran de l'Application

### Écran Principal
![Ecran Principal](app/src/screenshots/Homepage.jpg) 
Interface d'accueil montrant les différentes catégories de films avec défilement horizontal.

### Détails des Films
![Détails des Films](app/src/screenshots/filmdetails.jpg)
![Détails des Films](app/src/screenshots/filmdetails2.jpg)
Vue détaillée d'un film avec affiche, synopsis, notation et boutons d'action et l'ajout au liste des Favoris.

### Gestion des Favoris
![Liste des Favoris](app/src/screenshots/favlist.jpg)
![Liste des Favoris](app/src/screenshots/favlist2.jpg)
![Liste des Favoris](app/src/screenshots/favlist3.jpg)
Liste des films favoris avec fonctionnalité glisser-supprimer.

### Page de Paiement
![Page de paiement](app/src/screenshots/paiementpage.jpg)
![Page de paiement](app/src/screenshots/paiementpage2.jpg)
![Page de paiement](app/src/screenshots/paiementpage3.jpg)
![Page de paiement](app/src/screenshots/paiementpage4.jpg)
Interface de sélection d'abonnement VIP et de paiement.

### Confirmation de Paiement
![Page de paiement](app/src/screenshots/paiementpage5.jpg)
Page de confirmation après un paiement réussi.

### Connexion Utilisateur
![Page de paiement](app/src/screenshots/loginpage.jpg)
Formulaire d'authentification utilisateur.



## 🛠️ Stack Technique

- **Langage** : Kotlin
- **SDK Minimum** : 24 (Android 7.0)
- **SDK Cible** : 36
- **Architecture** : Pattern MVC
- **Chargement d'Images** : Picasso
- **Layout** : ConstraintLayout + LinearLayout
- **Listes** : RecyclerView avec adaptateurs personnalisés
- **Stockage** : SharedPreferences pour les favoris
- **Réseau** : Permission Internet pour le chargement d'images

## 📦 Dépendances

**dependencies :**
implementation("com.google.code.gson:gson:2.10.1")
implementation("com.squareup.picasso:picasso:2.71828")
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.material)
implementation(libs.androidx.activity)
implementation(libs.androidx.constraintlayout)
implementation("androidx.recyclerview:recyclerview:1.3.2")
testImplementation(libs.junit)
androidTestImplementation(libs.androidx.junit)
androidTestImplementation(libs.androidx.espresso.core)


## 🔧 Installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/StailiSaad/AppFilm
 
