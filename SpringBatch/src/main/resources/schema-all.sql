DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT NOT NULL,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

alter table people add primary key(person_id);
ALTER TABLE springbatch.people MODIFY COLUMN person_id bigint auto_increment NOT NULL;