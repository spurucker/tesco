CREATE TABLE url (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  value VARCHAR(250) NOT NULL unique,
  shortened INT NOT NULL,
  accessed INT NOT NULL
);

