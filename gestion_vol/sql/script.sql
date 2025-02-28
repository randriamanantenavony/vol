create DATABASE vol;
-- \c vol;

-- Création des tables (en suivant ta structure)
CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    mail VARCHAR(255) NOT NULL,
    mdp VARCHAR(255) NOT NULL
);

CREATE TABLE modele (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL
);

CREATE TABLE type_siege (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL
);

CREATE TABLE avion (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    id_modele INT REFERENCES modele(id),
    date_fabrication DATE
);

CREATE TABLE avion_detail (
    id SERIAL PRIMARY KEY,
    id_avion INT REFERENCES avion(id),
    id_type_siege INT REFERENCES type_siege(id),
    nombre INT NOT NULL
);

CREATE TABLE passager (
    id SERIAL PRIMARY KEY,
    mail VARCHAR(255) NOT NULL
);

CREATE TABLE ville (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    pays VARCHAR(255) NOT NULL
);

CREATE TABLE vol (
    id SERIAL PRIMARY KEY,
    dateHeureDepart TIMESTAMP NOT NULL,
    dateHeureArrivee TIMESTAMP NOT NULL,
    id_avion INT REFERENCES avion(id),
    statut VARCHAR(100) DEFAULT 'Valide',
    id_ville_depart INT REFERENCES ville(id),
    id_ville_arrivee INT REFERENCES ville(id)
);

 alter table vol add column statut VARCHAR(20) DEFAULT 'Valide';

CREATE TABLE vol_detail (
    id SERIAL PRIMARY KEY,
    id_vol INT REFERENCES vol(id),
    id_type_siege INT REFERENCES type_siege(id),
    prix DECIMAL(10, 2) NOT NULL,
    nombre_disponible INT NOT NULL
);

select * FROM vol_detail 
JOIN type_siege t on id_type_siege = t.id;

CREATE TABLE etat_reservation (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL
);

CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    dateHeure TIMESTAMP NOT NULL,
    id_passager INT REFERENCES passager(id),
    passeport_img VARCHAR(100),
    id_etat_reservation INT REFERENCES etat_reservation(id)
);

CREATE TABLE reservation_detail (
    id_reservation_detail SERIAL PRIMARY KEY,
    id_reservation INT REFERENCES reservation(id),
    id_vol INT REFERENCES vol(id),
    id_type_siege INT REFERENCES type_siege(id),
    nombre INT NOT NULL
);

CREATE TABLE promotion (
    id SERIAL PRIMARY KEY,
    dateHeure TIMESTAMP NOT NULL,
    date_expiration TIMESTAMP NOT NULL,
    id_vol INT REFERENCES vol(id),
    id_type_siege INT REFERENCES type_siege(id),
    nombre INT NOT NULL,
    pourcentage_promotion DECIMAL(5, 2) NOT NULL
);

CREATE TABLE reservation_configuration (
    id SERIAL PRIMARY KEY,
    heure_limite_reservation INT NOT NULL,
    heure_limite_annulation INT NOT NULL,
    date_creation TIMESTAMP DEFAULT NOW()
);

-- Insertion des données

-- Admin
INSERT INTO admin (mail, mdp) VALUES
('vony@hotmail.com', 'boule');

-- Modèles d'avion
INSERT INTO modele (libelle) VALUES
('Boeing 737'),
('Airbus A320');

-- Types de sièges
INSERT INTO type_siege (libelle) VALUES
('Economique'),
('Affaires');

-- Avions
INSERT INTO avion (nom, id_modele, date_fabrication) VALUES
('Avion 1', 1, '2020-01-01'),
('Avion 2', 2, '2019-05-15');

-- Détails des avions (nombre de sièges par type)
INSERT INTO avion_detail (id_avion, id_type_siege, nombre) VALUES
(1, 1, 150), 
(1, 2, 20),  
(2, 1, 120),
(2, 2, 30); 

-- Villes
INSERT INTO ville (nom, pays) VALUES
('Paris', 'France'),
('New York', 'Etats-Unis'),
('Tokyo', 'Japon');

-- Vols
INSERT INTO vol (dateHeureDepart, dateHeureArrivee, id_avion, id_ville_depart, id_ville_arrivee) VALUES
('2023-12-01 08:00:00', '2023-12-01 16:00:00', 1, 1, 2), -- Paris -> New York
('2023-12-02 10:00:00', '2023-12-02 18:00:00', 2, 2, 3); -- New York -> Tokyo

INSERT INTO vol (dateHeureDepart, dateHeureArrivee, id_avion, statut, id_ville_depart, id_ville_arrivee)
VALUES ('2025-03-01 13:26:25', '2025-03-01 15:26:25',1, 'Valide', 1, 2);

-- Détails des vols (prix et sièges disponibles)
INSERT INTO vol_detail (id_vol, id_type_siege, prix, nombre_disponible) VALUES
(1, 1, 500.00, 150), 
(1, 2, 1200.00, 20), 
(2, 1, 600.00, 120),
(2, 2, 1500.00, 30);

-- Passagers
INSERT INTO passager (mail) VALUES
('jean.dupont@example.com'),
('sophie.martin@example.com');

-- États de réservation
INSERT INTO etat_reservation (libelle) VALUES
('Validée'),
('Annulée');

-- Réservations
INSERT INTO reservation (dateHeure, id_passager, id_etat_reservation) VALUES
('2023-11-01 10:00:00', 1, 1),
('2023-11-02 11:00:00', 2, 1); 

-- Détails des réservations
INSERT INTO reservation_detail (id_reservation, id_vol, id_type_siege, nombre) VALUES
(3, 1, 1, 2), 
(4, 2, 2, 1);

-- Promotions
INSERT INTO promotion (dateHeure, date_expiration, id_vol, id_type_siege, nombre, pourcentage_promotion) VALUES
('2023-11-01 00:00:00', '2023-11-15 23:59:59', 1, 1, 10, 10.00); -- Promotion de 10% sur 10 sièges Économiques du Vol 1
INSERT INTO promotion (
    dateHeure, 
    date_expiration, 
    id_vol, 
    id_type_siege, 
    nombre, 
    pourcentage_promotion
) 
VALUES (
    CURRENT_TIMESTAMP,                -- dateHeure actuelle
    '2025-12-31 23:59:59',            -- date_expiration (exemple : fin d'année 2025)
    1,                                -- id_vol (id du vol concerné)
    2,                                -- id_type_siege (id du type de siège concerné)
    100,                              -- nombre (nombre de sièges promotionnels disponibles)
    20.00                             -- pourcentage_promotion (20% de réduction)
);

-- Configuration des réservations
INSERT INTO reservation_configuration (heure_limite_reservation, heure_limite_annulation) VALUES
(24, 12);


-- view
CREATE or replace view v_detail_vol AS
SELECT v.id,dateheuredepart,dateheurearrivee,v.id_avion ,a.nom as nom_avion,id_ville_depart , vd.nom as ville_depart, id_ville_arrivee , va.nom as ville_arrivee,statut
from vol v
JOIN avion a on a.id = v.id_avion 
JOIN ville vd on vd.id = id_ville_depart 
JOIN ville va on va.id = id_ville_arrivee;



create or replace view v_detail_promotion as 
SELECT 
    vd.id_vol, 
    vd.id_type_siege, 
    ts.libelle AS type_siege, 
    vd.prix, 
    p.pourcentage_promotion,
    nombre as nombre_siege, 
    vd.prix * (1 - p.pourcentage_promotion / 100) AS prix_reel
FROM 
    vol_detail vd
JOIN 
    type_siege ts ON vd.id_type_siege = ts.id
JOIN 
    promotion p ON vd.id_vol = p.id_vol AND vd.id_type_siege = p.id_type_siege;

CREATE VIEW vue_reservation_details AS
SELECT 
    r.id AS reservation_id,
    r.dateHeure AS reservation_date,
    r.passeport_img AS reservation_passeport_img,
    rd.id_reservation_detail,
    v.id AS vol_id,
    v.dateHeureDepart AS vol_depart,
    v.dateHeureArrivee AS vol_arrivee,
    ts.libelle AS type_siege,
    rd.nombre AS nombre_sieges
FROM 
    reservation r
JOIN 
    reservation_detail rd ON r.id = rd.id_reservation
JOIN 
    vol v ON rd.id_vol = v.id
JOIN 
    type_siege ts ON rd.id_type_siege = ts.id
JOIN 
    etat_reservation er ON r.id_etat_reservation = er.id;


SELECT * FROM v_detail_vol 
WHERE 1=1 
AND dateheuredepart >= '2025-02-22 00:00:00' 
AND dateheurearrivee <= '2025-02-23 23:59:00' 
AND id_avion = 1 
AND id_ville_depart = 2 
AND id_ville_arrivee = 1;
