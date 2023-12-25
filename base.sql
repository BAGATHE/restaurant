sudo -u postgres psql

CREATE USER restaurant WITH PASSWORD 'restaurant';

CREATE DATABASE restaurant;

GRANT ALL PRIVILEGES ON DATABASE restaurant to restaurant;

psql -U postgres -h localhost -p 5432 restaurant;

/*create table for restaurant*/



/*ny restaurant dian manana client*/
CREATE TABLE Client(
    idClient VARCHAR(255) PRIMARY KEY);

    CREATE SEQUENCE seqClient;

INSERT INTO Client (idClient) VALUES
('client1'),

/* ny menu anle restaurant mis categorie*/
CREATE TABLE Categorie(
idCategorie VARCHAR(255) PRIMARY KEY,
nomCategorie VARCHAR(255) NOT NULL UNIQUE);
CREATE SEQUENCE seqCategorie;


INSERT INTO Categorie (idCategorie, nomCategorie) VALUES
('categorie-1', 'entrer'),
('categorie-2', 'Plat principal'),
('categorie-3', 'Dessert'),
('categorie-4', 'Boisson');



/*ny menu dia misy karazany*/

CREATE TABLE Menu(
    idMenu VARCHAR(255)  PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL,
    prixUnitaire REAL NOT NULL CHECK (prixUnitaire > 0),
    prixIntermediaire REAL NOT NULL CHECK (prixIntermediaire> 0),
    prixReviens REAL NOT NULL CHECK (prixReviens > 0),
    idCategorie VARCHAR(255) NOT NULL,
    FOREIGN KEY(idCategorie) REFERENCES Categorie(idCategorie));
CREATE SEQUENCE seqMenu;

INSERT INTO Menu (idMenu, nom, prixUnitaire, prixIntermediaire, prixReviens, idCategorie) VALUES
('Menu-1', 'Boisson STAR GM', 8000,6000,4500, 'categorie-4'),
('Menu-2', 'Boisson STAR PM', 6000 ,5000, 2500, 'categorie-4'),
('Menu-3', 'FRESH',7000 ,6000,4500,'categorie-4'),
('Menu-4', 'Jus Naturel (Caraffe)', 10000, 8000, 4000, 'categorie-4'),
('Menu-5', 'Black Label', 250000,240000, 200000, 'categorie-4'),
('Menu-6', 'Vin Rouge', 90000, 80000, 60000, 'categorie-4'),

('Menu-7', 'Mille Feuille', 8000,7000,4500, 'categorie-3'),
('Menu-8', 'Yaourt au Fruit', 10000 ,8500, 6000, 'categorie-3'),
('Menu-9', 'Glace en Verre',12000 ,11000,8000,'categorie-3'),
('Menu-10', 'Salade De Fruit', 10000, 8000, 6000, 'categorie-3'),

('Menu-11', 'Riz Cantonnais au Poulet',18000, 17000, 8000, 'categorie-2'),
('Menu-12', 'Mandoto Nify au Coco', 16000,14000, 7000, 'categorie-2'),
('Menu-13', 'Birianni Foza De luxe', 18000, 15000, 12000, 'categorie-2'),
('Menu-14', 'Poulet Grille', 19000,16000,10000, 'categorie-2'),
('Menu-15', 'Filet De Zebu', 20000,19000,12000, 'categorie-2'),
('Menu-16', 'Cote Paner', 22000 ,20000, 13000, 'categorie-2'),
('Menu-17', 'Riz au Tsa-Tsiou',23000 ,20000,14000,'categorie-2'),

('Menu-18', 'Pomme Fritte ', 6000 ,5000, 2500, 'categorie-1'),
('Menu-19', 'Pack Nem',7000 ,6000,4500,'categorie-1'),
('Menu-20', 'Pack Sambos', 10000, 8000, 4000, 'categorie-1'),
('Menu-21', 'Pack Van-tan', 6000,4000, 3000, 'categorie-1'),
('Menu-22', 'Bouchon au Crevette', 9000, 8000, 6000, 'categorie-1');


INSERT INTO Menu (idMenu, nom, prixUnitaire, prixIntermediaire, prixReviens, idCategorie) VALUES
('Menu-1', 'PLAT A',18000 ,15800,14000,'categorie-1'),
('Menu-2', 'PLAT B' ,25000 ,22300, 20000, 'categorie-1'),
('Menu-3', 'PLAT C',32000 ,30000,28000,'categorie-1'),
('Menu-4', 'PLAT D', 17000, 16200, 15000, 'categorie-1'),
('Menu-5', 'PLAT E', 8000,5000, 3000, 'categorie-1');


/**ny client dia mana commande a la  consommation*/ 
CREATE TABLE Commande(
    idCommande VARCHAR(255) PRIMARY KEY,
    idMenu VARCHAR(255) NOT NULL,
    idFacture VARCHAR(255) NOT NULL,
    prixUnitaire REAL NOT NULL CHECK(prixUnitaire > 0),
    prixIntermediaire REAL NOT NULL CHECK(prixIntermediaire>0),
    prixReviens REAL NOT NULL CHECK(prixReviens > 0),
    quantiter INTEGER NOT NULL CHECK(quantiter > 0),
    dateCommande DATE NOT NULL,
    idClient VARCHAR(255) NOT NULL,
    idIntermediaire VARCHAR(255) NULL,
   
    FOREIGN KEY(idMenu) REFERENCES Menu(idMenu),
    FOREIGN KEY(idClient) REFERENCES Client(idClient),
    FOREIGN KEY(idFacture) REFERENCES Facture(idFacture));
CREATE SEQUENCE seqCommande;

INSERT INTO Commande (idCommande, idMenu, prixUnitaire, prixIntermediaire, prixReviens, quantiter, dateCommande, idIntermediaire, idClient) VALUES
('commande1', 'menu1', 10.0, 15.0, 8.0, 2, '2023-01-01', 'intermediaire1', 'client1'),
('commande2', 'menu2', 2.5, 4.0, 1.5, 3, '2023-02-01', 'client2', ''),
('commande3', 'menu3', 5.0, 8.0, 3.0, 1, '2023-03-01', 'intermediaire3', 'client3');

insert 




/* ary mis koa ireo intermediaire izay mitondra client potentiel ho any orinansa*/
CREATE TABLE Intermediaire(
     idIntermediaire VARCHAR(255) PRIMARY KEY,
     cin VARCHAR(255) NOT NUll,
     mdp VARCHAR(255) NOT NULL,
     quotaMax REAL NOT NULL CHECK (quotaMax > 0),
     quotaMin REAL NOT NULL CHECK (quotaMin < quotaMax));

CREATE SEQUENCE seqIntermediaire;

INSERT INTO Intermediaire (idIntermediaire, cin, mdp, quotaMax, quotaMin) VALUES
('intermediaire-1', '12345','12345', 20000000,140000),
('intermediaire-2', '789', '789', 200000000, 140000);






CREATE TABLE Facture(
    idFacture VARCHAR(255) PRIMARY KEY,
    idClient VARCHAR(255) NOT NULL,
    idIntermediaire VARCHAR(255),
     dateFacturation DATE NOT NUll,
    montantTotal REAL NOT NULL CHECK( montantTotal >= 0),
    FOREIGN KEY(idClient) REFERENCES Client(idClient));

CREATE SEQUENCE seqFacture;

INSERT INTO Facturation (idFacturation, montantTotal, idClient, idIntermediaire, dateFacturation) VALUES
('facture1', 35.0, 'client1', 'intermediaire1', '2023-01-10'),
('facture2', 20.0, 'client2', 'intermediaire2', '2023-02-15'),
('facture3', 16.0, 'client3', 'intermediaire3', '2023-03-20');


SELECT
    idintermediaire,
    SUM(prixnormal) AS somme_prix_normal,
    SUM(prixintermediaire) AS somme_prix_intermediaire,
    MAX(quotamax) AS max_quotamax,
    MIN(quotamin) AS min_quotamin
FROM
    (SELECT
        idintermediaire,
        idcommande,
        prixunitaire * quantiter AS prixnormal,
        prixintermediaire * quantiter AS prixintermediaire,
        datecommande,
        quotamax,
        quotamin
    FROM
        commande
    NATURAL JOIN
        intermediaire
    WHERE
        EXTRACT(MONTH FROM datecommande) = 12) AS subquery
GROUP BY
    idintermediaire;
