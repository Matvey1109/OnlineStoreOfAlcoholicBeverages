-- Procedures

-- #1
CREATE OR REPLACE PROCEDURE update_beverage_stock(
    beverage_id INT,
    new_quantity INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE "Beverage"
    SET available_quantity = new_quantity,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = beverage_id;
END;
$$;

CALL update_beverage_stock (9, 150);

-- #2
CREATE OR REPLACE PROCEDURE assign_discount_to_beverage(
    beverage_id INT,
    discount_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "BeverageDiscount" (beverage_id, discount_id, created_at, updated_at)
    VALUES (beverage_id, discount_id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
END;
$$;

CALL assign_discount_to_beverage (9, 2);

-- #3
CREATE OR REPLACE PROCEDURE add_beverage(
    beverage_name VARCHAR,
    beverage_description VARCHAR,
    beverage_price DECIMAL,
    available_qty INT,
    beverage_brand VARCHAR,
    alcohol_pct DECIMAL,
    category_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "Beverage" (name, description, price, available_quantity, brand, alcohol_percentage, created_at, updated_at, category_id)
    VALUES (beverage_name, beverage_description, beverage_price, available_qty, beverage_brand, alcohol_pct, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, category_id);
END;
$$;

CALL add_beverage (
    'Super cocktail',
    'Smooth and smoky',
    45.99,
    100,
    'Jameson',
    40.00,
    1
);

-- #4
CREATE OR REPLACE PROCEDURE add_user(
    user_email VARCHAR,
    user_password_hash TEXT,
    user_role_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO "User" (email, password_hash, created_at, updated_at, role_id)
    VALUES (user_email, user_password_hash, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, user_role_id);
END;
$$;

CALL add_user (
    'newuser@example.com',
    'hashed_password_4',
    1
);
