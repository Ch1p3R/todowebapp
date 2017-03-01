DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS account;

CREATE TABLE account(
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL
)engine=InnoDB;
CREATE TABLE todo(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  description VARCHAR(255) NOT NULL ,
  account_id Int(10) UNSIGNED NOT NULL,
  FOREIGN KEY (account_id) REFERENCES account(id)
)engine=InnoDB;
SHOW ENGINE INNODB STATUS;