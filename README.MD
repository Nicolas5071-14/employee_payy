# Système d'enregistrement Employeur - Travailleur
Un programme de gestion qui contient les informations des employés et des employeurs, ainsi que les enregistrements des relations entre eux.
---

# Branche v1
##Capture d'écran
<p align="center"><strong>Login</strong></p>
<p align="center"><img src="https://user-images.githubusercontent.com/71611710/157845415-c8f293df-5e1a-4ac5-a066-1971ee3ab6ae.png"></p>

| **Page d’accueil**            | **Enregistrement employeur**|  **Enregistrement travailleur**
:------------------------:|:------------------------:|:-------------------------:
![2-home_page](https://user-images.githubusercontent.com/71611710/157845986-0b99502d-ec6a-411c-999c-d37859dcf47e.png) | ![3-new_employer](https://user-images.githubusercontent.com/71611710/157849241-2a4ea23f-f195-4152-ab57-b2da20a1ea87.png)  |  ![3-new_worker](https://user-images.githubusercontent.com/71611710/157849850-5c6cfda1-05cd-4164-8287-474496cd189e.png)

| **Boîte de recherche**  | **Document d’enregistrement**
:----------------:|:-------------------------:
![5-view_worker](https://user-images.githubusercontent.com/71611710/157850829-c03944a1-bd1b-41d6-875b-61f8d8ce4d62.png) | ![7-new_record_optionpane](https://user-images.githubusercontent.com/71611710/158039292-30c103d1-bdaa-4f3f-bd36-342815fd6efd.png)

---

🧰 Prérequis
Ce programme utilise PostgreSQL comme base de données.

📦 Tu peux télécharger le fichier .jar nécessaire pour la connexion Java/PostgreSQL ici :
👉 https://jdbc.postgresql.org/download.html

🔧 Tu peux aussi utiliser une autre base de données, mais dans ce cas, il faudra modifier cette ligne :


DriverManager.getConnection("jdbc:database://host:port/database-name", "user-name", "password");

Exemple pour PostgreSQL :
DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", "postgres", "password")


