create database `softuni_stores_system`;



create table `pictures`(
`id` INT primary key auto_increment,
`url` varchar(100) NOT null,
`added_on` datetime NOT null
);


create table `categories`(
`id` INT primary key auto_increment,
`name` varchar(40) NOT null UNIQUE
);


create table `products`(
`id` INT primary key auto_increment,
`name`varchar(40) NOT null UNIQUE,
`best_before` date,
`price` DECIMAL(10 , 2) NOT NULL,
`description` text,
`category_id` INT NOT null,
`picture_id` INT NOT null,
CONSTRAINT `fk_products_categories` 
    FOREIGN KEY (`category_id`) 
        REFERENCES `categories` (`id`),
CONSTRAINT `fk_products_pictures` 
    FOREIGN KEY (`picture_id`) 
        REFERENCES `pictures` (`id`) 
);


create table `towns`(
`id` INT primary key auto_increment,
`name`varchar(20) NOT null UNIQUE
);


create table `addresses`(
`id` INT primary key auto_increment,
`name`varchar(50) NOT null UNIQUE,
`town_id` INT NOT NULL,
CONSTRAINT `fk_addresses_towns` 
    FOREIGN KEY (`town_id`) 
        REFERENCES `towns` (`id`)
);



create table `stores`(
`id` INT primary key auto_increment,
`name` varchar(20) NOT null UNIQUE,
`rating` float NOT NULL,
`has_parking` tinyint (1) default 0,
`address_id` INT NOT NULL,
CONSTRAINT `fk_stores_addresses` 
    FOREIGN KEY (`address_id`) 
        REFERENCES `addresses` (`id`)
);



create table `products_stores`(
`product_id` INT,
`store_id` INT,
primary KEY pk_products_stores (`product_id`,`store_id`), 
# primary KEY 
CONSTRAINT `fk_products_stores_products` 
    FOREIGN KEY (`product_id`) 
        REFERENCES `products` (`id`),
CONSTRAINT `fk_products_stores_stores` 
    FOREIGN KEY (`store_id`) 
        REFERENCES `stores` (`id`)
);



create table `employees`(
`id` INT primary key auto_increment,
`first_name` varchar(15) NOT null,
`middle_name` char (1),
`last_name` varchar(20) NOT null,
`salary` DECIMAL(19 , 2) default 0,
`hire_date` date NOT NULL,
`manager_id` INT,
`store_id` INT NOT NULL,
CONSTRAINT `fk_employees_stores` 
    FOREIGN KEY (`store_id`) 
        REFERENCES `stores` (`id`),
CONSTRAINT `fk_employees_manager`
    FOREIGN KEY (`manager_id`) 
        REFERENCES `employees` (`id`)
);

