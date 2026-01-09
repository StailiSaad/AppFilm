# 🎬 FilmApp — Application Android de Streaming de Films

FilmApp est une **application Android moderne développée en Kotlin**, orientée **UI/UX**, permettant aux utilisateurs de parcourir des films, gérer leurs favoris et souscrire à des abonnements VIP premium.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Picasso](https://img.shields.io/badge/Picasso-FF6D00?style=for-the-badge)

---

## 📋 Table des matières
- [🎯 Fonctionnalités](#-fonctionnalités)
- [🏗️ Architecture](#️-architecture)
- [📸 Captures d’écran](#-captures-décran)
- [🛠️ Stack technique](#️-stack-technique)
- [🔧 Installation](#-installation)
- [👥 Équipe](#-équipe-de-développement)
- [🔗 Contacts](#-contacts)
- [🙏 Remerciements](#-remerciements)

---

## 🎯 Fonctionnalités

### ✨ Fonctionnalités principales
- 🎬 Navigation des films par catégories (Tendances, Populaires, Action, Comédie…)
- 📖 Détails complets des films (description, note, genre, année)
- ⭐ Gestion des favoris avec swipe-to-delete
- 📝 Ajout & modification de films via un formulaire dédié
- 🔐 Authentification utilisateur
- 💎 Abonnements VIP premium (3 niveaux)

### 🎨 Expérience utilisateur (UX)
- 🌙 Mode sombre (Dark Mode) automatique (Day/Night)
- ↔️ Scroll horizontal fluide
- 💫 Animations RecyclerView (fade, swipe)
- 🖼️ Chargement d’images optimisé avec Picasso
- 📱 Design responsive compatible multi-écrans

### 💳 Système de paiement (simulation)
- 🏆 Niveaux VIP
  - Niveau 1 : 9,99 €/mois
  - Niveau 2 : 19,99 €/mois
  - Niveau 3 : 29,99 €/mois
- 💳 Paiement via Visa / MasterCard / PayPal
- 🔒 Validation complète des formulaires

---

## 🏗️ Architecture

<div align="center">
  <img src="app/src/screenshots/architectureprj.PNG" width="600">
</div>

- Architecture basée sur le **pattern MVC**
- Organisation modulaire des packages :
  - `Activities`
  - `Adapters`
  - `Models`
  - `Managers`
- Séparation claire des responsabilités
- Utilisation de **ViewBinding**

---

## 📸 Captures d’écran

### 📱 Interface principale

<div align="center">

| Accueil | Détails du film | Favoris |
|-------|----------------|---------|
| <img src="app/src/screenshots/Homepage.jpg" width="250"> | <img src="app/src/screenshots/filmdetails.jpg" width="250"> | <img src="app/src/screenshots/favlist.jpg" width="250"> |

</div>

### 💳 Paiement & Authentification

<div align="center">

| Paiement | Confirmation | Login |
|---------|-------------|-------|
| <img src="app/src/screenshots/paiementpage.jpg" width="250"> | <img src="app/src/screenshots/paiementpage5.jpg" width="250"> | <img src="app/src/screenshots/loginpage.jpg" width="250"> |

</div>

### ⭐ Favoris & interactions

<div align="center">

| Ajout favoris | Swipe suppression | Après suppression |
|--------------|------------------|------------------|
| <img src="app/src/screenshots/filmdetails2.jpg" width="200"> | <img src="app/src/screenshots/favlist2.jpg" width="200"> | <img src="app/src/screenshots/favlist3.jpg" width="200"> |

</div>

### 🚀 Splash Screen

<div align="center">
  <img src="app/src/screenshots/splashscreen.jpeg" width="200">
</div>

---

## 🛠️ Stack technique

### 🔧 Technologies
- Langage : **Kotlin**
- SDK minimum : **24 (Android 7.0)**
- SDK cible : **36**
- UI : **Material Design 3**
- Navigation : **Navigation Component**
- Images : **Picasso**
- Listes : **RecyclerView**
- Stockage : **SharedPreferences**
- Documentation : **KDoc + Dokka**

### 📦 Dépendances principales
```kotlin
implementation("androidx.core:core-ktx:1.12.0")
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.11.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
implementation("com.squareup.picasso:picasso:2.8")
implementation("com.google.code.gson:gson:2.10.1")
implementation("androidx.core:core-splashscreen:1.0.1")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

```
## 🔧 Installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/StailiSaad/AppFilm

# 📄 Copyright & Informations de l'Équipe

## 👥 Équipe de Développement

**Équipe AppFilm**  
*Développement d'Applications Mobiles*

### 🧑‍💻  Développeurs Principaux
**Saad Staili**

 

 
## 🔗 Contacts

**Pour toute question ou collaboration:**
- 📧 Email : saadstaili1903@gmail.com
- 🐙 GitHub :[StailiSaad](https://github.com/StailiSaad)

## 🙏 Remerciements

**Remerciements spéciaux à:**
- L'équipe pédagogique pour le support et les conseils
- La communauté Android pour les ressources partagées
- Les contributeurs open-source pour les librairies utilisées

---
 

**Dernière mise à jour : 27/11/2025**
