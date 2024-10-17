

INSERT INTO `towns`(`id`, name)
VALUES (1, 'Sofia'), (2, 'Plovdiv'), (3, 'Varna');

-- idnameagetown_id 
INSERT INTO `minions`(`id`, `name`, `age`, `town_id`)
VALUES (1, 'Kevin', 22, 1),
(2, 'Bob', 15, 3),
(3, 'Steward', NULL, 2);

truncate table `minions`;

DROP table `minions`;
DROP table `towns`;

DROP database `minions`;

CREATE database `exercise`;

CREATE table `people`(
`id`INT primary key auto_increment,
`name`varchar(200) NOT null,
`picture` BLOB,
`height` DOUBLE (10, 2),
`weight`DOUBLE (10, 2),
`gender`CHAR(1) NOT NULL,
`birthdate`DATE NOT NULL,
`biography` TEXT
);

INSERT INTO `people`(`name`, `gender`, `birthdate`)
VALUES ('Boris', 'm', date(now())), 
('Alex', 'm', date(now())),
('Ana', 'f', date(now())),
('Ina', 'f', date(now())),
('Ivan', 'm', date(now()))
;

CREATE table `users`(
`id`INT primary key auto_increment,
`username`varchar(30) NOT NULL,
`password` varchar(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` TIME,
`is_deleted` boolean

);
INSERT INTO `users`(`username`, `password`)
VALUES ('Sasha', 'slog2369'),
('ava', 'avaNew'),
('bet', 'tree'),
('beti', 'date7'),
('rado', '23yet')
;

ALTER table `users`
DROP primary key,
ADD primary key pk_users (`id`, `username`);


