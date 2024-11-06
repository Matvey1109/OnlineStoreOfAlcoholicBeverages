-- Simple queries

SELECT name, price FROM "Beverage";

SELECT * FROM "Category" ORDER BY name DESC;

SELECT * FROM "User" WHERE role_id = 1;

SELECT AVG(salary) as "Average Salary" FROM "Employee"

SELECT * FROM "Beverage" WHERE alcohol_percentage > 20.0;

SELECT * FROM "Order" WHERE price > 40 ORDER BY price ASC;

SELECT name, percent, is_active
FROM "Discount"
WHERE
    is_active = TRUE
    AND percent > 10;

SELECT * FROM "User" WHERE created_at > '2022-01-01';

SELECT * FROM "Review" WHERE rating BETWEEN 3 AND 5;

SELECT * FROM "Category" WHERE name LIKE 'W%';

SELECT *
FROM "Client"
WHERE
    id IN (
        SELECT DISTINCT
            client_id
        FROM "Order"
    )
ORDER BY id ASC;

SELECT
    beverage_id,
    COUNT(rating) as "Number of ratings"
FROM "Review"
GROUP BY
    beverage_id

SELECT category_id, AVG(price) as "Average Price"
FROM "Beverage"
GROUP BY
    category_id
HAVING
    AVG(price) > 5;

SELECT * FROM "CartItem"
WHERE cart_id in (
    SELECT id FROM "Cart"
    where client_id = 1
)
limit 3;
