-- Script de cr�ation de la base de donn�es ENCHERES
--   type :      SQL Server 2012
--
DBCC CHECKIDENT ('CATEGORIES', RESEED, 0);
DBCC CHECKIDENT ('ENCHERES', RESEED, 0);
DBCC CHECKIDENT ('UTILISATEURS', RESEED, 0);
DBCC CHECKIDENT ('ARTICLES_VENDUS', RESEED, 0);


DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS ROLES;
 




CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)
 
ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)
 
CREATE TABLE ENCHERES (
	no_enchere   INTEGER IDENTITY(1,1) NOT NULL,
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     DATETIME2  NOT NULL,
	montant_enchere  INTEGER NOT NULL
 
)
 
ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_enchere)
 
CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)
 
ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)
 
CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(50) NOT NULL,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(100) NOT NULL, 
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)
 
ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)
 
 
CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATETIME2 NOT NULL,
    date_fin_encheres             DATETIME2 NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)
 
ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)
 
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT article_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action
 
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY (no_article)
        REFERENCES ARTICLES_VENDUS (no_article)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
 
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_utilisateur)
        REFERENCES UTILISATEURS (no_utilisateur)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION;
 
CREATE TABLE ROLES (
    role VARCHAR(30) NOT NULL,
    is_admin BIT NOT NULL
);

 
 
ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action
 
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action
 
    
  ALTER TABLE ARTICLES_VENDUS    ADD CONSTRAINT chk_dates_encheres CHECK (date_debut_encheres < date_fin_encheres);
  
  ALTER TABLE ENCHERES    ADD CONSTRAINT chk_montant_enchere CHECK (montant_enchere > 0);
  
  
  INSERT INTO CATEGORIES (libelle) VALUES ('Informatique');
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement');
INSERT INTO CATEGORIES (libelle) VALUES ('Vêtement');
INSERT INTO CATEGORIES (libelle) VALUES ('Sport&Loisirs');

INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_USER', 0);
INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_ADMIN', 1);
INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_USER', 1);	
 


INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
('admin1', 'Admin', 'Super', 'admin@example.com', '0600000001', '1 rue de l Admin', '75001', 'Paris', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 10000, 1),
('user1', 'Durand', 'Jean', 'user1@example.com', '0600000002', '2 rue de Paris', '75002', 'Paris', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 1000, 0),
('user2', 'Dupont', 'Marie', 'user2@example.com', '0600000003', '3 rue de Lyon', '69001', 'Lyon', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 1000, 0),
('user3', 'Martin', 'Paul', 'user3@example.com', '0600000004', '4 rue de Marseille', '13001', 'Marseille', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 1000, 0),
('user4', 'Lemoine', 'Sophie', 'user4@example.com', '0600000005', '5 avenue de Lille', '59000', 'Lille', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 1000, 0),
('user5', 'Morel', 'Luc', 'user5@example.com', '0600000006', '6 rue de Bordeaux', '33000', 'Bordeaux', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user6', 'Fournier', 'Elise', 'user6@example.com', '0600000007', '7 rue de Nantes', '44000', 'Nantes', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user7', 'Girard', 'Hugo', 'user7@example.com', '0600000008', '8 rue de Rennes', '35000', 'Rennes', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user8', 'Bonnet', 'Alice', 'user8@example.com', '0600000009', '9 avenue de Strasbourg', '67000', 'Strasbourg', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user9', 'Garnier', 'Emma', 'user9@example.com', '0600000010', '10 rue de Toulouse', '31000', 'Toulouse', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user10', 'Lambert', 'Nathan', 'user10@example.com', '0600000011', '11 boulevard de Nice', '06000', 'Nice', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user11', 'Chevalier', 'Léa', 'user11@example.com', '0600000012', '12 rue de Montpellier', '34000', 'Montpellier', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user12', 'Roux', 'Lucas', 'user12@example.com', '0600000013', '13 avenue de Reims', '51100', 'Reims', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user13', 'Blanc', 'Chloé', 'user13@example.com', '0600000014', '14 boulevard de Tours', '37000', 'Tours', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user14', 'Henry', 'Gabriel', 'user14@example.com', '0600000015', '15 rue de Dijon', '21000', 'Dijon', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user15', 'Lopez', 'Zoé', 'user15@example.com', '0600000016', '16 avenue de Grenoble', '38000', 'Grenoble', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user16', 'Dupuis', 'Tom', 'user16@example.com', '0600000017', '17 boulevard de Brest', '29200', 'Brest', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user17', 'Marchand', 'Jade', 'user17@example.com', '0600000018', '18 rue de Limoges', '87000', 'Limoges', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user18', 'Bertrand', 'Mathis', 'user18@example.com', '0600000019', '19 avenue de Clermont', '63000', 'Clermont-Ferrand', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0),
('user19', 'Collet', 'Sarah', 'user19@example.com', '0600000020', '20 rue de Metz', '57000', 'Metz', '{bcrypt}$2a$10$Fu/9x1LZ6nTvwN4RlR2pXe9MLVP80pfFDE5y8mnqbU.vo8mjj7vSG', 100, 0);


INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES
('Ordinateur Portable', 'PC portable performant avec processeur i7 et SSD 512Go.', '2025-02-27 08:00:00', '2025-02-28 20:00:00', 800, NULL, 1, 1),
('Table Basse', 'Table basse en bois massif, style scandinave.', '2025-02-12 09:30:00', '2025-02-22 21:00:00', 150, NULL, 2, 2),
('T-shirt Noir', 'T-shirt en coton bio, taille M.', '2025-02-11 10:00:00', '2025-02-18 19:30:00', 20, NULL, 3, 3),
('Vélo de Route', 'Vélo de course en carbone, très léger.', '2025-02-13 07:45:00', '2025-02-25 22:15:00', 1200, NULL, 4, 4),
('Casque Gaming', 'Casque audio avec micro et réduction de bruit.', '2025-02-15 12:00:00', '2025-02-28 23:00:00', 90, NULL, 5, 1),
('Chaise Design', 'Chaise moderne avec assise rembourrée.', '2025-02-14 08:30:00', '2025-02-24 18:45:00', 80, NULL, 6, 2),
('Jean Slim', 'Jean coupe slim, couleur bleu foncé.', '2025-02-10 14:00:00', '2025-02-19 20:30:00', 50, NULL, 7, 3),
('Raquette de Tennis', 'Raquette légère et maniable pour joueurs confirmés.', '2025-02-16 15:30:00', '2025-02-26 19:45:00', 200, NULL, 8, 4),
('Montre Connectée', 'Montre intelligente avec suivi d activité et notifications.', '2025-02-17 09:15:00', '2025-02-27 20:45:00', 250, NULL, 9, 1),
('Canapé 3 Places', 'Canapé en tissu gris avec coussins confortables.', '2025-02-18 11:00:00', '2025-02-28 22:00:00', 500, NULL, 10, 2),
('Sweat à Capuche', 'Sweat unisexe en coton, couleur gris chiné.', '2025-02-19 13:30:00', '2025-02-28 23:59:00', 40, NULL, 11, 3),
('Tapis de Yoga', 'Tapis antidérapant idéal pour le yoga et le fitness.', '2025-02-20 07:00:00', '2025-02-28 21:15:00', 30, NULL, 12, 4),
('Smartphone', 'Téléphone haut de gamme avec écran OLED.', '2025-02-21 16:45:00', '2025-02-28 23:30:00', 900, NULL, 13, 1),
('Bureau en Bois', 'Bureau minimaliste en chêne avec tiroirs.', '2025-02-22 10:30:00', '2025-02-28 18:00:00', 300, NULL, 14, 2),
('Chaussures de Sport', 'Baskets légères pour la course à pied.', '2025-02-23 08:45:00', '2025-02-28 19:30:00', 120, NULL, 15, 3),
('Sac à Dos', 'Sac à dos robuste pour ordinateur et accessoires.', '2025-02-24 14:15:00', '2025-02-28 20:00:00', 70, NULL, 16, 1),
('Lunettes de Soleil', 'Lunettes de soleil polarisées avec protection UV.', '2025-02-25 09:00:00', '2025-02-28 18:45:00', 60, NULL, 17, 3),
('Tondeuse à Gazon', 'Tondeuse électrique puissante et silencieuse.', '2025-02-22 11:30:00', '2025-02-28 22:30:00', 400, NULL, 18, 4),
('Casque Bluetooth', 'Casque sans fil avec autonomie de 30h.', '2025-02-15 17:00:00', '2025-02-17 23:45:00', 150, NULL, 19, 1),
('Armoire Penderie', 'Grande armoire en bois avec miroir.', '2025-02-20 08:00:00', '2025-02-28 21:00:00', 600, NULL, 20, 2);








INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
(1, 'Rue Lafayette', '75001', 'Paris'),
(2, 'Avenue des Champs', '75002', 'Paris'),
(3, 'Boulevard Saint-Michel', '75003', 'Paris'),
(4, 'Rue de la Paix', '75004', 'Paris'),
(5, 'Quai de la Tournelle', '75005', 'Paris'),
(6, 'Rue de Rivoli', '75001', 'Paris'),
(7, 'Place de l Opéra', '75002', 'Paris'),
(8, 'Avenue de l Opéra', '75003', 'Paris'),
(9, 'Rue Saint-Denis', '75004', 'Paris'),
(10, 'Boulevard Haussmann', '75009', 'Paris'),
(11, 'Place Vendôme', '75001', 'Paris'),
(12, 'Rue du Faubourg Saint-Honoré', '75008', 'Paris'),
(13, 'Avenue Montaigne', '75008', 'Paris'),
(14, 'Rue Saint-Honoré', '75001', 'Paris'),
(15, 'Boulevard des Italiens', '75002', 'Paris'),
(16, 'Rue de la Boétie', '75008', 'Paris'),
(17, 'Rue de Rennes', '75006', 'Paris'),
(18, 'Boulevard Voltaire', '75011', 'Paris'),
(19, 'Avenue de Clichy', '75017', 'Paris'),
(20, 'Rue de la Convention', '75015', 'Paris');


INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES
(4, 2, '2025-02-13 09:30:00', 160),
(5, 2, '2025-02-14 14:00:00', 170),
(6, 3, '2025-02-11 08:45:00', 25),
(7, 3, '2025-02-12 16:15:00', 30),
(8, 4, '2025-02-14 17:00:00', 1250),
(9, 4, '2025-02-15 12:30:00', 1300),
(10, 5, '2025-02-16 14:45:00', 95),
(11, 5, '2025-02-17 11:00:00', 100),
(12, 6, '2025-02-14 16:30:00', 85),
(13, 6, '2025-02-15 10:00:00', 90),
(14, 7, '2025-02-10 13:30:00', 55),
(15, 7, '2025-02-11 14:45:00', 60),
(16, 8, '2025-02-16 15:00:00', 210),
(17, 8, '2025-02-17 18:15:00', 220),
(18, 9, '2025-02-17 09:00:00', 260),
(19, 9, '2025-02-18 10:30:00', 280),
(20, 10, '2025-02-18 13:45:00', 520),
(1, 10, '2025-02-19 17:00:00', 550),
(3, 11, '2025-02-19 11:30:00', 45),
(5, 11, '2025-02-20 13:00:00', 50),
(7, 12, '2025-02-20 14:30:00', 35),
(9, 12, '2025-02-21 08:00:00', 40),
(11, 13, '2025-02-21 12:45:00', 920),
(13, 13, '2025-02-22 16:00:00', 950),
(15, 14, '2025-02-22 09:30:00', 320),
(17, 14, '2025-02-23 11:15:00', 340),
(19, 15, '2025-02-23 12:00:00', 130),
(1, 15, '2025-02-24 15:30:00', 140),
(2, 16, '2025-02-24 16:00:00', 75),
(4, 16, '2025-02-25 09:45:00', 80),
(6, 17, '2025-02-25 10:15:00', 65),
(8, 17, '2025-02-26 12:00:00', 70),
(10, 18, '2025-02-26 14:45:00', 420),
(12, 18, '2025-02-27 09:30:00', 450),
(14, 19, '2025-02-16 15:15:00', 160),
(16, 19, '2025-02-17 10:45:00', 170);

