

-- first name starts with "Sa" 
select `first_name`, `last_name`
from `employees`
where `first_name` like 'Sa%'
order by `employee_id`;


# 2. Find Names of All Employees by Last Name contains "ei" 
select `first_name`, `last_name`
from `employees`
where `last_name` like '%ei%'
order by `employee_id`;



# 3. Find First Names of All Employees in the departments 
-- with ID 3 or 10 and whose hire year is between 1995 and 2005
select `first_name`
from `employees`
where `department_id` in (3,10)
and 
year(hire_date) between 1995 and 2005
order by `employee_id`;


-- find the first and last names of all employees 
-- whose job titles does not contain "engineer". Order the information by id
select `first_name`, `last_name`
from `employees`
where `job_title` not like '%engineer%'
order by `employee_id`;


-- find town names that are 5 or 6 symbols
-- long and order them alphabetically by town name
select `name`
from `towns`
where char_length(`name`) = 5 or char_length(`name`) = 6
order by `name`;


-- all towns that start with letters M, K, B or E 
-- (case insensitively). Order them alphabetically by town name
select * from `towns`
where `name` like ('m%') or `name` like ('k%')  or `name` like ('b%') or `name` like ('e%')
order by `name`;


-- find all towns that do not start with letters R, B or D 
-- (case insensitively). Order them alphabetically by name
select * from `towns`
where `name` not like ('r%') and `name` not like ('b%')  and `name` not like ('d%') 
order by `name`;


-- to create view v_employees_hired_after_2000 with the first 
-- and the last name of all employees hired after 2000 year
CREATE VIEW `v_employees_hired_after_2000` AS
select `first_name`, `last_name` from `employees`
where year(hire_date) > 2000
order by `hire_date`;
select * from v_employees_hired_after_2000;


-- find the first and last names 
-- of all employees whose last name is exactly 5 characters long
select `first_name`, `last_name`
from `employees`
where char_length(`last_name`) = 5;



-- Find all countries that hold the letter 'A' 
-- in their name at least 3 times (case insensitively), 
-- sorted by ISO code
-- Display the country name and the ISO code
select `country_name`, `iso_code`
from `countries`
where `country_name` like '%a%a%a%'
order by `iso_code`;



-- Combine all peak names with all river names, 
-- so that the last letter of each peak name is the same
-- as the first letter of its corresponding river name. 
-- Display the peak name, the river name, and 
-- the obtained mix(converted to lower case). 
-- Sort the results by the obtained mix alphabetically. 
select `peak_name`, `river_name`,
CONCAT(LOWER(`peak_name`), SUBSTRING(LOWER(`river_name`),2)) AS mix
from `peaks`, `rivers`
WHERE
    RIGHT(`peak_name`, 1) = LEFT(LOWER(`river_name`), 1)
ORDER BY mix;



-- Find the top 50 games ordered by start date, then by name. 
-- Display only the games from the years 2011 and 2012. 
-- Display the start date in the format "YYYY-MM-DD". 
select `name`, `start`
from `games`
where year(start) between 2011 and 2012
order by date_format(`start`, '%Y-%m %d'), `name`
limit 50;

-- OR
SELECT `name`, date_format(`start`, '%Y-%m-%d') AS `start` FROM `games`
WHERE year(`start`) >= 2011 AND year(`start`) <= 2012  
ORDER BY `start`, `name`
LIMIT 50;


-- Find information about the email providers of all users. 
-- Display the user_name and the email provider. 
-- Sort the results by email provider alphabetically, then by username. 
select `user_name`, substring(`email`, locate('@', email) + 1) AS 'Email Provider'
from `users`
order by `Email Provider`, `user_name`;


-- Find the user_name and the ip_address for each user, 
-- sorted by user_name alphabetically. Display only the rows, 
-- where the ip_address matches the pattern: "___.1%.%.___". 
select `user_name`, `ip_address`
from `users`
where `ip_address` like '___.1%.%.___'
order by `user_name`;


-- Find all games with their corresponding part of the day and duration. 
-- Parts of the day should be Morning (start time is >= 0 and < 12), 
-- Afternoon (start time is >= 12 and < 18), Evening (start time is >= 18 and < 24). 
-- Duration should be Extra Short (smaller or equal to 3), 
-- Short (between 3 and 6 including), Long (between 6 and 10 including) 
-- and Extra Long in any other cases or without duration. 
select `name` as 'game',
case 
when hour(`start`) < 12 then 'Morning'
when hour(`start`) < 18 then 'Afternoon'
else 'Evening'
End 
AS 'Part of the Day',
case 
WHEN `duration` <= 3 THEN 'Extra Short'
WHEN `duration` <= 6 THEN 'Short'
WHEN `duration` <= 10 THEN 'Long'
ELSE 'Extra Long'
END 
AS 'Duration'
from `games`;



-- You are given a table orders (id, product_name, order_date) filled with data.
--  Consider that the payment for an order must be accomplished within 3 days 
-- after the order date. Also the delivery date is up to 1 month. 
-- Write a query to show 
-- each product's name, order date, pay and deliver due dates. 
SELECT `product_name`, `order_date`,
adddate(`order_date`, interval 3 day) as pay_due, 
adddate(`order_date`, interval 1 month) as deliver_due
FROM `orders`;










