
CREATE SCHEMA taskmate;

CREATE TABLE taskmate.user(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `fname` VARCHAR(255) NOT NULL,
                          `lname` VARCHAR(255) NOT NULL,
                          `email` VARCHAR(255) NOT NULL,
                          `password` VARCHAR(255) NOT NULL,
                          PRIMARY KEY (`id`));

CREATE TABLE taskmate.project(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `user_id` INT,
                          `project_name` VARCHAR(255) NOT NULL,
                          `description` VARCHAR(255),
                          `start_date` DATE,
                          `end_date` DATE,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`user_id`) REFERENCES user(`id`));

CREATE TABLE taskmate.section(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `project_id` INT,
                          `section_name` VARCHAR(255) NOT NULL,
                          `description` VARCHAR(255),
                          `start_date` DATE,
                          `end_date` DATE,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`project_id`) REFERENCES project(`id`));

CREATE TABLE taskmate.activity(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `section_id` INT,
                          `activity_name` VARCHAR(255) NOT NULL,
                          `description` VARCHAR(255),
                          `duration` FLOAT,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`section_id`) REFERENCES section(`id`));

CREATE TABLE taskmate.task(
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `activity_id` INT,
                          `task_name` VARCHAR(255) NOT NULL,
                          `description` VARCHAR(255),
                          `duration` FLOAT,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`activity_id`) REFERENCES activity(`id`));

CREATE TABLE taskmate.activity_assignment(
                                        `user_id` INT,
                                        `activity_id` INT,
                                        `hours_assigned` FLOAT,
                                        PRIMARY KEY (`user_id`, `activity_id`));

CREATE TABLE taskmate.task_assignment(
                                        `user_id` INT,
                                        `task_id` INT,
                                        `hours_assigned` FLOAT,
                                        PRIMARY KEY (`user_id`, `task_id`));

CREATE TABLE taskmate.team_member(
                                  `user_id` INT,
                                  `project_id` INT,
                                  `team_role` VARCHAR(255),
                                   PRIMARY KEY (`user_id`, `project_id`));

CREATE TABLE taskmate.team_member(
                                  `user_id` INT,
                                  `project_id` INT,
                                  `team_role` VARCHAR(255),
                                   PRIMARY KEY (`user_id`, `project_id`));

ALTER TABLE taskmate.activity
ADD COLUMN `status` INT DEFAULT 0;

ALTER TABLE taskmate.task
ADD COLUMN `status` INT DEFAULT 0;
