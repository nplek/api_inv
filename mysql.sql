CREATE TABLE dev_users (
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (email)
);
   
CREATE TABLE authorities (
  email VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (email) REFERENCES dev_users(email)
);
 
CREATE UNIQUE INDEX ix_auth_email on authorities (email,authority);

INSERT INTO dev_users (name, email, password, enabled)
  values ('user',
    'user@email.com',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a',
    1);
 
INSERT INTO authorities (email, authority)
  values ('user@email.com', 'ROLE_USER');

CREATE TABLE cosmetic (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  amount DOUBLE NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO cosmetic (name,amount) values ("CosmeticA", 200);