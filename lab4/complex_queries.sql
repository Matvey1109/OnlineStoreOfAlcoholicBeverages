-- Complex queries

SELECT * FROM "Beverage" WHERE available_quantity > 0 AND price < 20;

SELECT *
FROM "Beverage"
WHERE
    id IN (
        SELECT beverage_id
        FROM "OrderItem"
    );

-- JOINS

SELECT "Beverage".name, "Category".name
FROM "Beverage"
    INNER JOIN "Category" ON "Beverage".category_id = "Category".id;

SELECT "Beverage".name, "OrderItem".beverage_quantity
FROM "Beverage"
    RIGHT OUTER JOIN "OrderItem" ON "Beverage".id = "OrderItem".beverage_id;

-- INCLUSIVE
SELECT "Beverage".name, "OrderItem".beverage_quantity
FROM "Beverage"
    FULL OUTER JOIN "OrderItem" ON "Beverage".id = "OrderItem".beverage_id;

-- EXCLUSIVE
SELECT "Beverage".name, "OrderItem".beverage_quantity
FROM "Beverage"
    FULL OUTER JOIN "OrderItem" ON "Beverage".id = "OrderItem".beverage_id
WHERE
    "Beverage".id IS NULL
    OR "OrderItem".beverage_id IS NULL;

SELECT "Beverage".name, "Employee".first_name
FROM "Beverage"
    CROSS JOIN "Employee";

SELECT e1.first_name AS employee1, e2.first_name AS employee2
FROM "Employee" e1, "Employee" e2
WHERE
    e1.id <> e2.id;

-- GROUPING

SELECT
    beverage_id,
    COUNT(rating) as "Number of ratings"
FROM "Review"
GROUP BY
    beverage_id

SELECT
    name,
    price,
    SUM(price) OVER (
        PARTITION BY
            brand
    ) AS total_price_per_brand
FROM "Beverage";

SELECT brand, AVG(price) AS avg_price
FROM "Beverage"
GROUP BY
    brand
HAVING
    AVG(price) > 15
ORDER BY avg_price ASC;

SELECT id, name
FROM "Beverage"
UNION -- UNION ALL FOR SAVING DUBLICATES
SELECT id, name
FROM "Category"
ORDER BY id ASC;

-- More complex

SELECT *
FROM "Client" c
WHERE
    EXISTS (
        SELECT 1
        FROM "Order" o
        WHERE
            o.client_id = c.id
            AND o.price > 40
    );

INSERT INTO
    "Employee" (
        first_name,
        last_name,
        salary,
        position,
        email
    )
SELECT
    first_name,
    last_name,
    salary * 15,
    position,
    REVERSE(email) AS reversed_email
FROM "Employee"
WHERE
    salary > 48000;

SELECT
    name,
    CASE
        WHEN alcohol_percentage > 15 THEN 'Strong'
        ELSE 'Non-Strong'
    END AS category
FROM "Beverage";

EXPLAIN ANALYSE SELECT * FROM "Beverage" WHERE price < 20;

-- Task

SELECT cl.name as client_name, b.name as beverage_name, b.price AS price, d.percent AS discount
FROM
    "CartItem" ci
    INNER JOIN "Beverage" b ON ci.beverage_id = b.id
    INNER JOIN "Cart" c ON ci.cart_id = c.id
    LEFT JOIN "BeverageDiscount" bd ON b.id = bd.beverage_id
    LEFT JOIN "Discount" d ON bd.discount_id = d.id
    LEFT JOIN "Client" cl on c.client_id = cl.id;
