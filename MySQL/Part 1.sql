

select title 
from books
where substring(title, 1, 3) = 'The';



select replace( title, 'The', '***') as 'title'
from books
where substring(title, 1, 3) = 'The';


select LTRIM(RTRIM('  trimmed   '));



select RTRIM ('  trimmed   ');



select `title`, char_length(`title`)
from `books`;


select left (`title`, 3)
from `books`;


select right (`title`, 3)
from `books`;


select substring( `title`, -3)
from `books`;

select substring( `title`, -5, 3)
from `books`;


select `title`
from `books`
where upper(substring(`title`, 1, 5)) = 'HARRY';


select reverse ('Harry');



select locate ('Ha', 'Harry');


select insert ('Harry Potter', 1, 0, 'The ');
-- 0 позиция указва да не изтрива нищо вмъквайки
-- новата дума


select 13 / 5;
-- когато делим между числа, едно 
-- от които е с запетая

select 13 div 5;
-- когато делим цели числа 
-- и искаме цяло число резултат


select 13.0 div 5.0;
-- пак получаваме цяло число


select 13 mod 5;
-- търсим остатък само, тоест 5 се събира 
-- 2 пъти в 13 и остава 3


 select pi();
 
 
 select abs(5), abs(-5);
 -- връща винаги положителна стойност


select sqrt(9);
-- кое число на квадрат дава 9


select pow(5, 3);
-- искаме да повдигнем 5 на 3 степен

select round(5.245788, 3);
-- закръгли до 3 знак


select floor(5.245788);
-- закръгля до най-близкото цяло число


select CEILING(5.145788);
-- закръгля нагоре


select round(sum(`cost`), 2)
from `books`;

select extract(year from '2024-02-20'); 


select extract(minute from '2024-02-20 19:30'); 

select abs(TIMESTAMPDIFF(minute, '2024-02-20', '2024-02-19'));
-- дава ни разликата от дата до дата и му слагаме и abs
 


select concat_ws(' ', `first_name`, `last_name`) as 'Full Name',
 TIMESTAMPDIFF(day, `born`, `died`) as '•	Days Lived '
 from `authors`;
 
 
 
 select now();
 -- получаваме текущата дата и час, минути
 
 
 select date_format(`born`, '%D %M %Y') 
 from `authors`;
 
 
 select `title`
 from `books`
 where `title` like 'Harry Potter%';
 -- търсим с подобно заглавие в заглавията
 
 
