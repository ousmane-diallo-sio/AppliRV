## AppliRV
Application Android de gestion développée sous **Android Studio** `(Java)`.

Cette application est basée sur le contexte GSB et exploite la base de données d'un **serveur REST** dont le lien est renseigné ci-dessous :
- https://github.com/ousmane-diallo-sio/GSB-RV-Visiteur-Serveur/


<br>Ce projet réponds aux cas d’utilisation suivants :
- S'authentifier
- Consulter un rapport de visite
- Saisir un rapport de visite


### Description des cas d’utilisations :

| **S’authentifier** | 
| :------------- | 
| Acteur déclencheur : Visiteur |
| Le cas commence lorsque le visiteur demande à se connecter |
| Pré-conditions : néant |
| Post-conditions : Le visiteur est authentifié |
| <br> |
| **Scénario nominal :** <br> 
1 - Le système demande le matricule.
2 - Le visiteur saisit son matricule.
3 - Le système demande se mot de passe.
4 - Le visiteur saisit son mot de passe.
5 - Le système contrôle le matricule et le mot de passe.
6 - Le système affiche le menu de l’application.|
| <br> |
| **Scénario alternatif A5.a :** Le matricule est inconnu ou le mot de passe est incorrect
5.a.1 - Le système informe l’utilisateur de l’échec de l’authentification.
5.a.2 - Le cas d’utilisation reprend à l’étape 1 du scénario nominal.

<br><br>

| **Consulter un rapport de visite** |
| :------------ |
| Acteur déclencheur : Visiteur |
| Le cas commence quand le visiteur demande à consulter un de ses rapports de visite. |
| Pré-conditions : Le visiteur est authentifié |
| Post-conditions : néant |
| <br> |
| **Scénario nominal :** <br>
1 - Le système demande le mois et l’année.
2 - Le visiteur sélectionne le mois et l’aanée.
3 - Le système affiche la liste des rapports de visite du visiteur.
4 - Le visiteur sélectionne un rapport de visite.
5 - Le système affiche le rapport de visite.
| <br> |
| **Contraintes :** <br>
La liste des rapports de visite est présentée dans l’ordre chronologique inverse (date de visite). 
Dans cette liste de rapports de visite, seuls la date de la visite et le nom du praticien sont précisés.
Lors de la consultation d’un rapport de visite, toutes les informations qui lui sont relatives sont affichés

<br><br>

| **Saisir un rapport de visite** |
| :--------- |
| Acteur déclencheur : Visiteur |
| Le cas commence quand le visiteur demande à saisir un nouveau rapport de visite. |
| Pré-conditions : Le visiteur est authentifié. |
| Post-conditions : Le rapport de visite est daté et enregistré. |
| <br> |
| **Scénario nominal :** <br> 
1 - Le système demande la date de visite.
2 - Le visiteur sélectionne la date de la visite.
3 - Le système affiche la liste des praticiens.
4 - Le visiteur sélectionne le praticien.
5 - Le système affiche la liste des motifs.
6 - Le visiteur sélectionne le motif.
7 - Le système demande le bilan de la visite.
8 - Le visiteur saisit le bilan de la visite.
9 - Le système demande le coefficient de confiance du praticien.
10 - Le visiteur saisit le coefficient de confiance du praticien.
11 - Le système affiche la liste des médicaments.
12 - Le visiteur sélectionne les médicaments qui ont fait l’objet d’une offre d'échantillons.
13 - Le système demande, pour chaque médicament, le nombre d'échantillons offerts.
14 - Le visiteur précise le nombre d’échantillons offerts pour chaque médicament.
15 - Le système date et enregistre le rapport de visite.
| <br> |
| **Scénario d’exception E1 :** Le visiteur annule la saisie du rapport de visite. <br>L’enchaînement peut démarrer aux points 2, 4, 6, 8, 10, 12 et 14 du scénario nominal
15 - Le système annule la saisie du rapport de visite.
16 - Le système indique que le rapport de visite n’a pas été enregistré.
| <br> |
| **Contraintes :** <br>
Tous les champs doivent être renseignés.
  
## Modélisation de la base de donnée :


## Contexte GSB :

Ce projet s’appuie sur le contexte GSB. Galaxy Swiss Bourdin est un laboratoire pharmaceutique dont l’activité est constituée par la production et la distribution de médicaments ainsi que par des visites médicales. 
L’objectif de l’entreprise GSB est d’optimiser son activité à travers l’utilisation de l’informatique. 
