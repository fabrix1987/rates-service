drop database if exists entropay;

CREATE DATABASE entropay DEFAULT CHARACTER SET utf8 ;

drop table if exists entropay.rate;

CREATE TABLE entropay.rate (
  id int(11) NOT NULL AUTO_INCREMENT,  
  rate decimal(19,7) DEFAULT NULL,
  buyCurrency varchar(3) DEFAULT NULL,
  sellCurrency varchar(3) DEFAULT NULL,
  validDate timestamp NULL ,
  file varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

commit;

use entropay;
