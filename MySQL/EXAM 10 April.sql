

create database `softuni_imdb`; -- БЕЗ ТОВА В ДЖЪДЖ!!!


create table `countries`(
`id` INT primary key auto_increment,
`name` varchar(30) NOT null UNIQUE,
`continent` varchar(30) NOT null,
`currency`varchar(5) NOT null
);


create table `genres`(
`id` INT primary key auto_increment,
`name` varchar(50) NOT null UNIQUE

);

 
create table `actors`(
`id` INT primary key auto_increment,
`first_name` varchar(50) NOT null,
`last_name` varchar(50) NOT null,
`birthdate` date NOT null,
`height`INT,
`awards`INT,
`country_id`INT NOT null,
CONSTRAINT `fk_actors_countries` 
    FOREIGN KEY (`country_id`) 
        REFERENCES `countries` (`id`) 

);


CREATE TABLE `movies_additional_info` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `rating` DECIMAL(10 , 2 ) NOT NULL,
    `runtime` INT NOT NULL,
    `picture_url` VARCHAR(80) NOT NULL,
    `budget` DECIMAL(10 , 2 ),
    `release_date` DATE NOT NULL,
    `has_subtitles` TINYINT(1),
    `description` TEXT
);



create table `movies`(
`id` INT primary key auto_increment,
`title`varchar(70) NOT null UNIQUE,
`country_id` INT NOT null,
`movie_info_id` INT NOT null UNIQUE,
CONSTRAINT `fk_movies_countries` 
    FOREIGN KEY (`country_id`) 
        REFERENCES `countries` (`id`),
CONSTRAINT `fk_movies_movies_additional_info` 
    FOREIGN KEY (`movie_info_id`) 
        REFERENCES `movies_additional_info` (`id`) 
);


create table `movies_actors`(
`movie_id` INT,
`actor_id` INT,
KEY pk_movies_actors (`movie_id`,`actor_id`), -- външен кии
CONSTRAINT `fk_movies_actors_movies` 
    FOREIGN KEY (`movie_id`) 
        REFERENCES `movies` (`id`),
        CONSTRAINT `fk_movies_actors_actors` 
    FOREIGN KEY (`actor_id`) 
        REFERENCES `actors` (`id`)
);


create table `genres_movies`(
`genre_id` INT,
`movie_id` INT,
KEY pk_genres_movies (`genre_id`,`movie_id`), -- външен кии, така е по условие
# primary KEY pk_genres_movies (`genre_id`,`movie_id`) 
-- примерен композитен праймъри кии, друго е от горното
CONSTRAINT `fk_genres_movies_genres` 
    FOREIGN KEY (`genre_id`) 
        REFERENCES `genres` (`id`),
CONSTRAINT `fk_genres_movies_movies` 
    FOREIGN KEY (`movie_id`) 
        REFERENCES `movies` (`id`)

);



# 2
-- first_name – set it to the first name of the actor but reversed.
-- last_name – set it to the last name of the actor but reversed.
#	birthdate – set it to the birthdate of the actor but 2 days earlier.
#	height – set it to the height of the actor plus 10.
-- awards – set them to the country_id.
-- country_id – set it to the id of Armenia.
-- actors with id equal or less than 10
INSERT INTO `actors`(`first_name`, `last_name`, `birthdate`, `height`, `awards`, `country_id`)
select (reverse(`first_name`)), (reverse(`last_name`)) ,
(date(birthdate - 2)),
(`height` + 10),
(`country_id`),
(3)
from `actors`
where `id` <= 10;



# 3
update `movies_additional_info`
set `runtime` = `runtime` - 10
where `id` between 15 and 25;


# 4
delete c from `countries` as c
left join `movies` as m
on c.`id` = m.`country_id`
where m.`country_id` is NULL;



# 5 Extract from the softuni_imdb system database, info about the name of countries.
-- •id (countries) •name •continent •currency
-- Order the results by currency in descending order and then by id.
Select * from `countries`
order by `currency` desc, `id`;


# 6  Old movies
-- Write a query that returns: title, runtime, budget and release_date 
-- from table movies_additional_info. 
-- Filter movies which have been released from 1996 to 1999 year (inclusive).
-- Order the results ascending by runtime then by id and show only the first 20 results.
select m.`id`, m.`title`, mi.`runtime`, mi.`budget`, mi.`release_date`
from movies as m
inner join movies_additional_info as mi
on m.`movie_info_id` = mi.`id`
where year(mi.`release_date`) between 1996 and 1999
order by mi.`runtime`, m.`id`
limit 20;



# 7 -- Write a query that returns:  full name, email, age and height for all actors 
-- that are not participating in a movie.
-- To find their email you must take 
-- their last name reversed followed by the number of characters 
-- of their last name and then the casting email “@cast.com”

-- full_name (first_name + " " + last_name)
-- email (last_name reversed + number of characters from the last_name + @cast.com)
-- age (2022 – the year of the birth)
-- height
-- Order by height in ascending order.
select concat(`first_name`, ' ', `last_name`)  as 'full_name', 
concat(reverse(`last_name`), length( `last_name`), '@cast.com')  as'email', 
2022 - year(`birthdate`) as 'age',
`height`
from `actors`
where `id` not in (select `actor_id` from `movies_actors`)
order by `height`;




-- 8 - GROUP, COUNT AND HAVING

select c.`name`, count(m.`id`) as 'movies_count' -- or count(m.`country_id`) 
from countries as c 
inner join movies as m 
on c.`id` = m.`country_id`
group by c.`name`
having `movies_count` >= 7
order by c.`name` desc;


-- 9 EXTRACT  
-- •	rating (less or equal to 4 – “poor”, 
-- above 4 and less or equal to 7 – “good”, 
-- above 7 – “excellent”)
select m.`title`,
	(case 
		when mi.`rating` <= 4 then 'poor'
		when mi.`rating` <= 7 then 'good'
		else 'excellent'
	end) as 'rating',
if(mi.`has_subtitles` = 1, 'english', '-') as 'subtitles',
mi.`budget`
from `movies` as m
join `movies_additional_info` as mi on m.`movie_info_id` = mi.`id`
order by mi.`budget` desc;

