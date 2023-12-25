Create table Restaurant
  -idRestaurant;
  -nomRestaurant;

create table categorie
 -id 
 -nom


Create table Menu
  -idMenu
  -idRestaurant
  -nomMenu
  -prix_unitaire
  -prix_Intermediaire
  -prix_Reviens


  create table intermediaire

  idIntermediaire;
  idRestaurant;   //mbola ts ao anatiny
  nomIntermediaire;
  quotamax;
  quotamin;
  String cin;
  String mdp;


  create table facture

    -idfacture
    -idRestaurant
    -idIntermediaire
    -date 

create table commande 
  -idCommande
  -idfacture
  -idMenu
  -quantiter
  -prix_unitaire
  -prix_Intermediaire
  -prix_Reviens