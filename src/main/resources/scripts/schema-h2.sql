
--
-- Table structure for table user
--
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  nom varchar(100) DEFAULT NULL,
  prenom varchar(100) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_nom_prenom_unique (nom,prenom)
) AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


--
-- Table structure for table car
--
DROP TABLE IF EXISTS car;
CREATE TABLE car (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  model varchar(100) NOT NULL,
  color varchar(100) DEFAULT NULL,
  id_user bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (id),
-- INDEX id_user_fk (id_user),
CONSTRAINT id_user_fk FOREIGN KEY (id_user) REFERENCES user (id) ON DELETE SET NULL ON UPDATE CASCADE
) AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
