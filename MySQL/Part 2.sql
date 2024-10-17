



SELECT 
e.`id` as 'No.', 
e.`first_name` as 'First name', 
e.`last_name` as 'Last name', 
e.`job_title` as 'Title'
FROM `employees` as e
ORDER BY `id`;


SELECT `id`,
    CONCAT_WS(' ', `first_name`, `last_name`) AS 'Full name',
    `job_title` AS 'job_title',
    `salary` AS 'salary'
FROM
    `employees`
WHERE
    `salary` > 1000
ORDER BY `id`;



SELECT DISTINCT
    `department_id`
FROM
    `employees`;
    
    
    
SELECT     `id`, `first_name`, `department_id`
FROM    `employees`
WHERE    `department_id` = 1  OR `department_id` = 2;


SELECT     `id`, `first_name`, `department_id`
FROM    `employees`
WHERE   NOT (`department_id` = 2  AND `first_name` = 'John');


SELECT *
FROM    `employees`
WHERE `department_id` = 4 and `salary` >= 1000
order by `id`;


CREATE VIEW `employees_from_1_3_4_salary_over_1000` AS
    SELECT 
        e.`id` AS 'No.',
        e.`first_name` AS 'First name',
        e.`last_name` AS 'Last name',
        e.`job_title` AS 'Title'
    FROM
        `employees` AS e
    WHERE
        `department_id` IN (1 , 3, 4)
            AND `salary` > 1000
    ORDER BY `first_name` ASC , `last_name` DESC;
    
    
    
    SELECT 
    *
FROM
    `employees_from_1_3_4_salary_over_1000`;
    
    
    
    SELECT * FROM `employees`
ORDER BY `salary` DESC
LIMIT 3;

CREATE VIEW `employees_oreder_by_salary_desc_limit_3` AS
SELECT * FROM `employees`
ORDER BY `salary` DESC
LIMIT 3;


update `employees`
set `salary` = `salary` + 100
where `job_title` = 'Manager';


delete from `employees`
where `department_id` = 1 or `department_id` = 2;

