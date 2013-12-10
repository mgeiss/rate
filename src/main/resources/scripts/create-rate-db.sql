/*
 * Copyright 2013 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
  duration         INTEGER                NOT NULL,
  created          TIMESTAMP              NOT NULL
);
