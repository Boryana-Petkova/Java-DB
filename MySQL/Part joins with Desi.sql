
-- 1 
#employee_id
#job_title
#address_id
#address_text
#Return the first 5 rows sorted by address_id in ascending order.
select e.`employee_id`, e.`job_title`, e.`address_id`, a.`address_text`
from employees as e
inner join addresses as a
on e.`address_id` = a.`address_id`
order by a.`address_id`
limit 5;

 
-- 2 exam
SELECT 
    e.`first_name`, e.`last_name`, t.`name` as 'town', a.`address_text`
FROM
    employees AS e
        INNER JOIN
    addresses AS a ON e.`address_id` = a.`address_id`
        INNER JOIN
    towns AS t ON a.`town_id` = t.`town_id`
ORDER BY e.`first_name` , e.`last_name`
LIMIT 5;


-- 3 exam
SELECT 
    e.`employee_id`, e.`first_name`, e.`last_name`, d.`name` as 'department_name'
FROM
    employees AS e
        INNER JOIN
    departments AS d ON e.`department_id` = d.`department_id`
	where d.`name` = 'Sales' -- Select only employees from the "Sales" department.
ORDER BY e.`employee_id` desc;


-- 4 -- salary higher than 15000, first 5 rows 
-- sorted by department_id in descending order.
SELECT 
    e.`employee_id`, e.`first_name`, e.`salary`, d.`name` as 'department_name'
FROM
    employees AS e
        INNER JOIN
    departments AS d ON e.`department_id` = d.`department_id`
	where e.`salary` > 15000
ORDER BY d.`department_id` desc
LIMIT 5;


-- 5 Only employees without a project. 
-- Return the first 3 rows sorted by employee_id in descending order.
SELECT 
    e.`employee_id`, e.`first_name`
FROM
    employees AS e
        left JOIN -- изваждаме от лявата таблица и ни показва всичко от дясната, 
        -- докато при inner показва само съвпадащите редове/записи в двете таблици
    employees_projects AS ep ON e.`employee_id` = ep.`employee_id`
		left join 
    projects as p on ep.`project_id` = p.`project_id`
ORDER BY e.`employee_id` desc
LIMIT 3;


-- 6 -- employees hired after 1/1/1999 
-- and from either the "Sales" or the "Finance" departments. Sort the result by hire_date (ascending).
SELECT 
    e.`first_name`, e.`last_name`, e.`hire_date`, d.`name` as 'dept_name'
FROM
    employees AS e
        inner join
    departments AS d ON e.`department_id` = d.`department_id`
    where e.`hire_date` > '1999-01-01 00:00:00' 
    and
		 d.`name` in ('Sales' , 'Finance')
ORDER BY e.`hire_date`;



-- 7
SELECT 
    e.`employee_id`, e.`first_name`, p.`name` as 'project_name'
FROM
    employees AS e
        JOIN 
    employees_projects AS ep ON e.`employee_id` = ep.`employee_id`
		join 
    projects as p on ep.`project_id` = p.`project_id`
    where p.`start_date` > '2002-08-13'
    and p.`end_date` is NULL
ORDER BY e.`first_name`
LIMIT 5;


-- 8 -- all the projects of employees with id 24. If the project has 
-- started after 2005 inclusively the return value should be NULL. Sort the result by project_name alphabetically.
SELECT 
    e.`employee_id`, e.`first_name`, IF(YEAR(p.`start_date`) >= 2005, NULL, p.`name`) AS 'project_name'
FROM
    employees AS e
        JOIN
    employees_projects AS ep ON e.`employee_id` = ep.`employee_id`
        JOIN
    projects AS p ON ep.`project_id` = p.`project_id`
WHERE
    e.`employee_id` = 24
ORDER BY p.`name`
LIMIT 5;



-- 9 
SELECT 
    e.`employee_id`, e.`first_name`, e.`manager_id`, m.`first_name` as 'manager_name'
FROM
    employees AS e
    join
    employees AS m on e.`manager_id` = m.`employee_id`
	WHERE e.`manager_id` in (3, 7)
ORDER BY e.`first_name`;




# 10. Employee Summary -- Show the first 5 employees (only for employees who have a manager) 
-- with their managers and the departments they are in (show the departments of the employees). 
-- Order by employee_id.
SELECT 
    e.`employee_id`,
    CONCAT(e.`first_name`, ' ', e.`last_name`) AS 'employee_name',
    CONCAT(m.`first_name`, ' ', m.`last_name`) AS 'manager_name',
    d.`name` AS 'department_name'
FROM `employees` AS e
JOIN `employees` AS m ON e.`manager_id` = m.`employee_id`
JOIN `departments` AS d ON e.`department_id` = d.`department_id`
ORDER BY e.`employee_id`
LIMIT 5;

 
# 11. Min Average Salary -- the value of the lowest average salary of all departments.
SELECT AVG(`salary`) AS 'min_average_salary'
FROM `employees`
GROUP BY `department_id`
ORDER BY `min_average_salary`
LIMIT 1;

 
# 12. Highest Peaks in Bulgaria 
-- all peaks in Bulgaria with elevation over 2835. Return all rows sorted by elevation in descending order.
SELECT c.`country_code`, m.`mountain_range`, p.`peak_name`, p.`elevation`
FROM `peaks` AS p
INNER JOIN `mountains` AS m ON p.`mountain_id` = m.`id`
INNER JOIN `mountains_countries` AS c ON m.`id` = c.`mountain_id`
WHERE c.`country_code` = 'BG' AND p.`elevation` > 2835
ORDER BY p.`elevation` DESC;


 
# 13. Count Mountain Ranges
-- count of the mountain ranges in the United States, Russia and Bulgaria. 
-- Sort result by mountain_range count in decreasing order.
SELECT c.`country_code`, COUNT(m.`mountain_range`) AS `mountain_range` FROM `mountains` AS m 
INNER JOIN `mountains_countries` AS c ON m.`id` = c.`mountain_id`
WHERE c.`country_code` IN ('BG', 'RU', 'US')
GROUP BY c.`country_code`
ORDER BY `mountain_range` DESC;


 
# 14. Countries with Rivers
-- first 5 countries with or without rivers in Africa. Sort them by country_name in ascending order.
SELECT c.`country_name`, r.`river_name`
FROM `countries` AS c
LEFT JOIN `countries_rivers` AS cr ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` AS r ON r.`id` = cr.`river_id`
WHERE c.`continent_code` = 'AF'
ORDER BY c.`country_name`
LIMIT 5;
 
 
 
# 16. Countries without any Mountains
-- count of all countries which don't have a mountain.
SELECT COUNT(c.`country_code`)
FROM `countries` AS c
LEFT JOIN `mountains_countries` AS mc ON c.`country_code` = mc.`country_code`
LEFT JOIN `mountains` AS m ON mc.`mountain_id` = m.`id`
WHERE m.`id` IS NULL;


 
# 17. Highest Peak and Longest River by Country
-- each country, find the elevation of the highest peak and the length of the longest river, 
-- sorted by the highest peak_elevation (from highest to lowest), 
-- then by the longest river_length (from longest to smallest), then by country_name (alphabetically). 
-- Display NULL when no data is available in some of the columns. Limit only the first 5 rows.
SELECT c.`country_name`, MAX(p.`elevation`) AS 'highest_peak_elevation',MAX(r.`length`) AS 'longest_river_length'
FROM `countries` AS c
LEFT JOIN `mountains_countries` AS mc ON c.`country_code` = mc.`country_code`
LEFT JOIN `peaks` AS p ON mc.`mountain_id` = p.`mountain_id`
LEFT JOIN `countries_rivers` AS cr ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` AS r ON cr.`river_id` = r.`id`
GROUP BY c.`country_name`
ORDER BY `highest_peak_elevation` DESC , `longest_river_length` DESC , c.`country_name`
LIMIT 5;


