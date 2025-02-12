-- Script de cr�ation de la base de donn�es ENCHERES
--   type :      SQL Server 2012
--
 
DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;
 
CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)
 
ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)
 
CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL
 
)
 
ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)
 
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
INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_USER', 0);
INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_ADMIN', 1);
INSERT INTO ROLES (role, is_admin) VALUES ('ROLE_USER', 1);	
 
 
 
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
 
ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action
    
  ALTER TABLE ARTICLES_VENDUS    ADD CONSTRAINT chk_dates_encheres CHECK (date_debut_encheres < date_fin_encheres);
  
  ALTER TABLE ENCHERES    ADD CONSTRAINT chk_montant_enchere CHECK (montant_enchere > 0);
 