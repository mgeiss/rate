CREATE DATABASE rate WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = - 1;

DROP SEQUENCE test_scenario_id_seq;

CREATE SEQUENCE test_scenario_id_seq;

DROP TABLE test_scenario;

CREATE TABLE test_scenario
(
  id            INTEGER PRIMARY KEY,
  name          CHARACTER VARYING(255) NOT NULL,
  description   CHARACTER VARYING(4000),
  host          CHARACTER VARYING(255) NOT NULL,
  path          CHARACTER VARYING(4000),
  working_hours CHARACTER VARYING(10)  NOT NULL,
  delay         CHARACTER VARYING(10)  NOT NULL,
  created       TIMESTAMP              NOT NULL
);

DROP SEQUENCE request_log_id_seq;

CREATE SEQUENCE request_log_id_seq;

DROP TABLE request_log;

CREATE TABLE request_log
(
  id               INTEGER DEFAULT NEXTVAL('request_log_id_seq') PRIMARY KEY,
  test_scenario_id INTEGER REFERENCES test_scenario (id),
  robot_name       CHARACTER VARYING(255) NOT NULL,
  sub_resource     CHARACTER VARYING(4000),
  request_type     CHARACTER VARYING(255) NOT NULL,
  send_bytes       INTEGER,
  received_bytes   INTEGER,
  status           INTEGER                NOT NULL,
  duration         BIGINT                 NOT NULL,
  created          TIMESTAMP              NOT NULL
);
