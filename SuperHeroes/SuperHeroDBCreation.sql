DROP DATABASE IF EXISTS HeroSightings_Test;

CREATE DATABASE IF NOT EXISTS HeroSightings_Test;

USE HeroSightings_Test;

CREATE TABLE Sighting
(
SightingID INT NOT NULL AUTO_INCREMENT,
LocationID INT NOT NULL, #FOREIGN KEY
`Date` DATE NOT NULL,
`Name` VARCHAR(50),

PRIMARY KEY (SightingID)
);

CREATE TABLE MetaHuman_Sighting_Bridge
(
MetaHuman_Sighting_BridgeID INT NOT NULL AUTO_INCREMENT,
SightingID INT NOT NULL, #FOREIGN KEY
MetaHumanID INT NOT NULL, #FOREIGN KEY

UNIQUE (MetaHumanID, SightingID),
PRIMARY KEY(MetaHuman_Sighting_BridgeID)
);

CREATE TABLE MetaHuman
(
MetaHumanID INT NOT NULL AUTO_INCREMENT,
`Name` VARCHAR(40) NOT NULL,
Identity VARCHAR(30),

PRIMARY KEY(MetaHumanID)
);

CREATE TABLE MetaHuman_Power_Bridge
(
MetaHumanPowerBridgeID INT NOT NULL AUTO_INCREMENT,
MetaHumanID INT NOT NULL, #FOREIGN KEY
PowerID INT NOT NULL, #FOREIGN KEY

UNIQUE (MetaHumanID, PowerID),
PRIMARY KEY(MetaHumanPowerBridgeID)
);

CREATE TABLE Power
(
PowerID INT NOT NULL AUTO_INCREMENT,
Description VARCHAR(100),

PRIMARY KEY(PowerID)
);

CREATE TABLE Organization
(
OrganizationID INT NOT NULL AUTO_INCREMENT,
`Name` VARCHAR(40) NOT NULL,
Description VARCHAR(100),
LocationID INT, #FOREIGN KEY

PRIMARY KEY(OrganizationID)
);

CREATE TABLE MetaHuman_Organization_Bridge
(
MetaHumanOrganizationBridgeID INT NOT NULL AUTO_INCREMENT,
MetaHumanID INT NOT NULL, #FOREIGN KEY
OrganizationID INT NOT NULL, #FOREIGN KEY

UNIQUE (MetaHumanID, OrganizationID),
PRIMARY KEY(MetaHumanOrganizationBridgeID)
);


CREATE TABLE Location
(
LocationID INT NOT NULL AUTO_INCREMENT,
Longitude VARCHAR(20),
Latitude VARCHAR(20),
Address VARCHAR (50),
`Name` VARCHAR(50),
Description VARCHAR(200),

PRIMARY KEY (LocationID)
);


ALTER TABLE MetaHuman_Organization_Bridge ###########################################################

ADD CONSTRAINT
fk_MetaHuman_Organization_Bridge_MetaHuman
FOREIGN KEY
(MetaHumanID)
REFERENCES
MetaHuman(MetaHumanID),

ADD CONSTRAINT
fk_MetaHuman_Organization_Bridge_Organization
FOREIGN KEY
(OrganizationID)
REFERENCES
Organization(OrganizationID);

ALTER TABLE Sighting ###########################################################

ADD CONSTRAINT
fk_Sighting_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);

ALTER TABLE MetaHuman_Sighting_Bridge ###########################################################

ADD CONSTRAINT
fk_MetaHuman_Sighting_Bridge_Sighting
FOREIGN KEY
(SightingID)
REFERENCES
Sighting(SightingID),

ADD CONSTRAINT
fk_MetaHuman_Sighting_Bridge_MetaHuman
FOREIGN KEY
(MetaHumanID)
REFERENCES
MetaHuman(MetaHumanID);

ALTER TABLE MetaHuman_Power_Bridge ###########################################################

ADD CONSTRAINT
fk_MetaHuman_Power_Bridge_MetaHuman
FOREIGN KEY
(MetaHumanID)
REFERENCES
MetaHuman(MetaHumanID),

ADD CONSTRAINT
fk_MetaHuman_Power_Bridge_Power
FOREIGN KEY
(PowerID)
REFERENCES
`Power`(PowerID);

ALTER TABLE Organization ###########################################################

ADD CONSTRAINT
fk_Organization_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);

INSERT INTO MetaHuman
(`Name`, Identity)
VALUES
('One-Punch Man', 'Saitama'),
('Dr. Neo Cortex', 'Dr. Neo Periwinkle Cortex'),
('Calendar Man', 'Julian Gregory Day'),
('Dr. Strange', 'Stephen Vincent Strange, M.D.'),
('Captain America', 'Steven Rogers');


INSERT INTO Location
(`Name`, Description, Address, Latitude, Longitude)
VALUES
('Stark Tower', 'Tony Stark''s Personal Tower', 'NY, NY', 'LAT:45°66.12 ''N', 'LON:76°87.32''W'),
('Hero Association Building', 'Building in City A', 'City A, Japan', 'LAT:3°66.12 ''N', 'LON:7°87.32''W'),
('City Park', 'By the old community center', 'Fuyuki, Japan', 'LAT:3°6.1 ''N', 'LON:7°8.3''W');
-- ('Unknown', 'Unknown', 'Unknown', 'Unknown', 'Unknown');


INSERT INTO Organization
(`Name`, Description, LocationID)
VALUES
('Hero Association', 'Organization founded by the multi-millionaire Agoni that manages all of the cities'' heroes.', 2),
('Super-genius Club', 'Works alone', NULL),
('The Misfits', 'A group of second rate criminals assembled by Killer Moth.', NULL),
('The Avengers', 'A fictional team of superheroes appearing in American comic books published by Marvel Comics', 1),
('The Illuminati', 'A group where each member represents a certain something that is very special to the Marvel Universe', 1);

INSERT INTO MetaHuman_Organization_Bridge
(MetaHumanID, OrganizationID)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(4, 5),
(5, 4);

INSERT INTO Power
(Description)
VALUES
('Can kill anything with one punch'),
('Genius-level intellect'),
('Ages with seasonal weather'),
('Skilled at magic'),
('User of magical artifacts'),
('Super Soldier');

INSERT INTO MetaHuman_Power_Bridge
(MetaHumanID, PowerID)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(4, 5),
(5, 6);

INSERT INTO Sighting
(`Name`, `Date`, LocationID)
VALUES
('Calendar Man VS. One Punch Man', '2017/09/28', 3),
('Dr. Strange VS. Dr. Neo Cortex', '2018/08/23', 2),
('Cap and Strange have a tiff', '2015/02/18', 1);

INSERT INTO MetaHuman_Sighting_Bridge
(SightingID, MetaHumanID)
VALUES
(1, 3),
(1, 1),
(2, 2),
(2, 4),
(3, 4),
(3, 5);