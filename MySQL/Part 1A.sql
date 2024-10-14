



select `first_name`, `middle_name`, `last_name`
 from `employees` 
order by `employee_id`;


select concat(`first_name`, '.', `last_name`, '@softuni.bg')
 as `full_email_address`
 from `employees`;
 
 
 select distinct (`salary`) 
 from `employees`;
 
 select * from `employees`
 where `job_title` = 'Sales Representative'
 order by `employee_id`;
 
 
 select `first_name`, `last_name`, `job_title`
 from `employees`
 where `salary`between 20000 and 30000
 order by `employee_id`;
 
 
 select concat(`first_name`,' ', `middle_name`, ' ', `last_name`)
 as `Full Name`
 from `employees`
 where `salary` in (25000, 14000, 12500, 23600);
 #where `salary` = 25000 or `salary` = 14000 
 #or `salary` = 12500 or `salary` = 23600;
 
 
 select `first_name`, `last_name` 
 from `employees`
 where `manager_id` is null;
 
 
 select `first_name`, `last_name`, `salary`
 from `employees`
 where `salary` > 50000
 order by `salary` desc;
 
 
 
 select `first_name`, `last_name`
 from `employees`
 where `department_id` != 4;
 
 
 
select * from `employees`
order by `salary` desc, `first_name`, `last_name` desc,
 `middle_name`, employee_id;
 
 
 create view v_employees_salaries  as 
 select 
 `first_name`, `last_name`, `salary`
 from `employees`;
 
 
 select * from v_employees_salaries;
 
 
 # AS and without Null in middle name
 create view v_employees_job_titles  as 
 select concat_ws(' ',
 `first_name`, `middle_name`, `last_name`) as `full name`, `job_title`
 from `employees`;
 
 
 # само уникалните взимаме
select distinct `job_title` from `employees`
 order by `job_title`;
 
 select * from `projects`
 order by `start_date`, `name`, `project_id`
 limit 10;
 


 select `first_name`, `last_name`, `hire_date` from `employees`
 order by `hire_date` desc
 limit 7;
 
 
 
 #Engineering, Tool Design, Marketing or Information Services
update `employees`
set `salary` = `salary` + `salary` * 0.12
where `department_id` in (1,2,4,11);
select `salary` from `employees`;


SELECT 
    `country_name`, `population`
FROM
    `countries`
WHERE
    `continent_code` = 'EU'
ORDER BY `population` DESC , `country_name`
LIMIT 30;


# AS
SELECT `country_name`, `country_code`, 
if (`currency_code` = 'EUR', 'Euro', 'Not Euro') as `currency` 
FROM `countries`
ORDER BY  `country_name`;


SELECT `name` from `characters`
order by `name`;



 
 