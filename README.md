# 🎬 Application Film - Application de Streaming de Films

Une application Android moderne de streaming de films développée en Kotlin qui permet aux utilisateurs de parcourir des films, gérer leurs favoris et souscrire à des abonnements VIP premium.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Picasso](https://img.shields.io/badge/Picasso-FF6D00?style=for-the-badge)

## 📋 Table des Matières
- [Fonctionnalités](#-fonctionnalités)
  - [Fonctionnalités Principales](#-fonctionnalités-principales)
  - [Expérience Utilisateur](#-expérience-utilisateur)
  - [Système de Paiement](#-système-de-paiement)
- [Architecture](#%EF%B8%8F-architecture)
- [Captures d'Écran](#-captures-décran)
  - [Interface Utilisateur](#-interface-utilisateur)
  - [Vues Supplémentaires](#-vues-supplémentaires)
  - [Documentation Technique](#-documentation-technique)
- [Stack Technique](#-stack-technique)
  - [Technologies Utilisées](#-technologies-utilisées)
  - [Dépendances](#-dépendances)
- [Installation](#-installation)
- [Équipe de Développement](#-équipe-de-développement)
- [Contacts](#-contacts)
- [Remerciements](#-remerciements)



## 🎯 Fonctionnalités

### ✨ Fonctionnalités Principales
- 🎬 Navigation des films par catégories (Tendances, Populaires, Action, Comédie…)
- 📖 Détails complets des films (description, note, genre ...)
- ⭐ Gestion des favoris avec swipe-to-delete
- 📝 Ajout & modification de films via un formulaire dédié
- 🔐 Authentification utilisateur
- 💎 Abonnements VIP premium (3 niveaux)

### 🎨 Expérience Utilisateur
- 🌙 Mode sombre (Dark Mode) automatique (Day/Night)
- ↔️ Scroll horizontal fluide
- 💫 Animations RecyclerView (fade, swipe)
- 🖼️ Chargement d’images optimisé avec Picasso
- 📱 Design responsive compatible multi-écrans

### 💳 Système de Paiement
- 🏆 Niveaux VIP
  - Niveau 1 : 9,99 €/mois
  - Niveau 2 : 19,99 €/mois
  - Niveau 3 : 29,99 €/mois
- 💳 Paiement via Visa / MasterCard / PayPal
- 🔒 Validation complète des formulaires

## 🏗️ Architecture


<div align="center">

![Architecture de l'Application](app/src/screenshots/architectureprj.PNG)


</div>

## 📸 Captures d'Écran

### 📱 Interface Utilisateur

<div align="center">

| Écran d'Accueil | Détails du Film |                    Liste des Favoris                    |
|:---------------:|:---------------:|:-------------------------------------------------------:|
| <img src="app/src/screenshots/Homepage.jpg" width="250"> | <img src="app/src/screenshots/filmdetails.jpg" width="250"> | <img src="app/src/screenshots/favlist.jpg" width="250"> |
| *Interface principale avec navigation par catégories* | *Détails complets du film avec notation* |              *Interface des Films Favoris*              |

| Processus de Paiement | Confirmation | Authentification |
|:---------------------:|:------------:|:----------------:|
| <img src="app/src/screenshots/paiementpage.jpg" width="250"> | <img src="app/src/screenshots/paiementpage5.jpg" width="250"> | <img src="app/src/screenshots/loginpage.jpg" width="250"> |
| *Sélection d'abonnement VIP* | *Paiement réussi* | *Connexion utilisateur* |

</div>

### 🔍 Vues Supplémentaires

<div align="center">

|              Détails Film et ajout aux Favoris               |               Favoris + Swipe(Suppression)               |           Favoris apres Suprresion des Films             |
|:------------------------------------------------------------:|:--------------------------------------------------------:|:--------------------------------------------------------:|
| <img src="app/src/screenshots/filmdetails2.jpg" width="200"> | <img src="app/src/screenshots/favlist2.jpg" width="200"> | <img src="app/src/screenshots/favlist3.jpg" width="200"> |
|                     *Ajout aux favoris*                      |          *Gestion des films favoris avec swipe*          |                    *Gestion avancée*                     |

|                         Paiement VISA                         |                      Paiement MASTERCARD                      |                        Paiement PAYPAL                        |
|:-------------------------------------------------------------:|:-------------------------------------------------------------:|:-------------------------------------------------------------:|
| <img src="app/src/screenshots/paiementpage2.jpg" width="200"> | <img src="app/src/screenshots/paiementpage2.jpg" width="200"> | <img src="app/src/screenshots/paiementpage4.jpg" width="200"> |
|                     *Champs a remplir*                     |                      *Champs a remplir*                       |                        *Finalisation*                         |


|                        Splash Screen                        |                               
|:-------------------------------------------------------------:| 
| <img src="app/src/screenshots/splashscreen.jpeg" width="200"> |    
|                     *Splash Screen*                     |                  


</div>

### 📚 Documentation Technique

<div align="center">

![Documentation Dokka](app/src/screenshots/dokka.PNG)
*Documentation technique générée par Dokka - Structure complète du code avec commentaires KDoc*

</div>

## 🛠️ Stack Technique

### 🔧 Technologies Utilisées
- **📝 Langage** : Kotlin
- **📱 SDK Minimum** : 24 (Android 7.0)
- **🎯 SDK Cible** : 36
- **🏗️ Architecture** : Pattern MVC
- **🖼️ Chargement d'Images** : Picasso
- **📐 Layout** : ConstraintLayout + LinearLayout
- **📋 Listes** : RecyclerView avec adaptateurs personnalisés
- **💾 Stockage** : SharedPreferences pour les favoris
- **🌐 Réseau** : Permission Internet pour le chargement d'images

### 📦 Dépendances principales

```kotlin
dependencies {
    

// UI & Navigation
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.11.0")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.recyclerview:recyclerview:1.3.2")
  implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
  implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

// Image Loading - PICASSO
  implementation("com.squareup.picasso:picasso:2.8")

// JSON Parsing
  implementation("com.google.code.gson:gson:2.10.1")

// Splash Screen API
  implementation("androidx.core:core-splashscreen:1.0.1")

// Coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
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
