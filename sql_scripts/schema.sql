DROP SCHEMA IF EXISTS `task_directory`;

CREATE SCHEMA `task_directory`;

use `task_directory`;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `user`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(45) UNIQUE NOT NULL,
                         `password` varchar(100) DEFAULT NULL,
                         `enabled` BOOLEAN DEFAULT TRUE,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task`(
                       `id` int NOT NULL AUTO_INCREMENT,
                       `task_description`varchar(100) DEFAULT NULL,
                       `task_status`varchar(50) DEFAULT NULL,
                       `task_priority`varchar(50) DEFAULT NULL,
                       `user_id` int DEFAULT NULL,

                       PRIMARY KEY(`id`),
                       KEY `FK_USER_idx` (`user_id`),

                       CONSTRAINT `FK_USER`
                           FOREIGN KEY (`user_id`)
                               REFERENCES `users`(`id`)

                               ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles`(
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `role` varchar(50) NOT NULL,
                        PRIMARY KEY(`id`),
                        FOREIGN KEY(`user_id`) REFERENCES `users`(`id`)
);


SET FOREIGN_KEY_CHECKS=1;