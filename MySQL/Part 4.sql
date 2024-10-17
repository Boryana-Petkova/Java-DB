
select `department_id`, sum(`salary`)
from `employees`
group by `department_id`;


select `department_id`, count(`id`)
from `employees`
group by `department_id`
order by `department_id` desc;




select `department_id`, round(avg(`salary`), 2)
from `employees`
group by `department_id`;



-- Min Salary
select `department_id`, round(MIN(`salary`), 2) 
-- 2 е закръгляне до 2 знака след запетайката
from `employees`
where `salary` > 800
group by `department_id`;


-- Min Salary with Having
select `department_id`, 
		round(MIN(`salary`), 2) as `minSalary`
from `employees`
group by `department_id`
having `minSalary` > 800;
-- ако е без having ще ни даде 
-- 3 най-ниски заплати по-големи от 800
-- докато с having от тези 3 заплати ще даде най-голямата 1700



-- a query to retrieve the count of 
-- all appetizers (category id = 2) with price higher than 8
select count(*)
from `products`
where `category_id` = 2 and `price` > 8;


-- information about the prices of each category 
-- The output should consist of:
-- Category_id
-- Average Price 
-- Cheapest Product
-- Most Expensive Product
-- See the examples for more information. 
-- Round the results to 2 digits after the decimal point
select `category_id`, 
	round(avg(`price`), 2) as `Average Price`,
    round(min(`price`),2) as `Cheapest Product`,
    round(max(`price`),2) as `Most Expensive Product`
from `products`
group by `category_id`;