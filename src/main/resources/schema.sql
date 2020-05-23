DROP TABLE if exists registrationplate;
DROP TABLE if exists insurer;
DROP TABLE if exists vehicle;


CREATE TABLE if not exists registrationplate(
   id INT NOT NULL, 
   platenumber VARCHAR(255) NOT NULL, 
   vehicleid INT
);


CREATE TABLE if not exists insurer(
   code INT NOT NULL, 
   name VARCHAR(255) NOT NULL
);


CREATE TABLE if not exists vehicle(
   id INT NOT NULL, 
   type VARCHAR(255) NOT NULL, 
   make VARCHAR(255) NOT NULL, 
   model VARCHAR(255) NOT NULL, 
   colour VARCHAR(255) NOT NULL, 
   vin VARCHAR(255) NOT NULL, 
   tareweight INT NOT NULL,
   grassmass int NOT NULL,
   registrationexpirydate timestamp,
   insurerid int not null
);