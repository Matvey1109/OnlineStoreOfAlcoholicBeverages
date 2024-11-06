-- Insert data

INSERT INTO
    "Beverage" (
        name,
        description,
        price,
        available_quantity,
        brand,
        alcohol_percentage,
        category_id
    )
VALUES (
        'Whiskey',
        'Smooth whiskey with rich flavor',
        25.00,
        50,
        'Jack Daniel''s',
        40.00,
        1
    ),
    (
        'Red Wine',
        'Dry red wine with a fruity aroma',
        15.00,
        100,
        'Merlot',
        12.50,
        3
    ),
    (
        'Vodka',
        'Pure and smooth vodka',
        20.00,
        80,
        'Absolut',
        40.00,
        1
    ),
    (
        'Margarita',
        'Classic tequila-based cocktail',
        12.00,
        60,
        'Jose Cuervo',
        20.00,
        2
    );

INSERT INTO
    "Category" (name)
VALUES ('Alcoholic Drinks'),
    ('Cocktails'),
    ('Wines');

INSERT INTO
    "User" (email, password_hash, role_id)
VALUES (
        'admin@gmail.com',
        'hashed_password_1',
        1
    ),
    (
        'employee@gmail.com',
        'hashed_password_2',
        2
    ),
    (
        'client@gmail.com',
        'hashed_password_3',
        3
    );

INSERT INTO
    "Employee" (
        first_name,
        last_name,
        salary,
        phone,
        position,
        email
    )
VALUES (
        'John',
        'Doe',
        50000.00,
        '1234567890',
        'Manager',
        'john.doe@example.com'
    ),
    (
        'Jane',
        'Smith',
        45000.00,
        '0987654321',
        'Bartender',
        'jane.smith@example.com'
    );

INSERT INTO
    "Client" (name, email)
VALUES (
        'Alice Johnson',
        'alice.johnson@example.com'
    ),
    (
        'Bob Brown',
        'bob.brown@example.com'
    );

INSERT INTO "Role" (name) VALUES ('Admin'), ('Employee'), ('Client');

INSERT INTO "Order" (price, client_id) VALUES (50.00, 1), (30.00, 2);

INSERT INTO
    "OrderItem" (
        beverage_quantity,
        beverage_price,
        beverage_id,
        order_id
    )
VALUES (1, 25.00, 9, 1),
    (1, 15.00, 10, 2);

INSERT INTO "Cart" (price, client_id) VALUES (37.00, 1), (20.00, 2);

INSERT INTO
    "CartItem" (
        beverage_quantity,
        beverage_price,
        beverage_id,
        cart_id
    )
VALUES (1, 25.00, 9, 3),
    (1, 15.00, 10, 4);

INSERT INTO
    "Review" (
        content,
        rating,
        beverage_id,
        client_id
    )
VALUES (
        'Excellent whiskey, very smooth.',
        5,
        9,
        1
    ),
    (
        'Not a fan of red wine, too dry for my taste.',
        3,
        10,
        2
    );

INSERT INTO
    "Discount" (
        name,
        description,
        percent,
        is_active
    )
VALUES (
        'Holiday Special',
        '10% off on all alcoholic beverages',
        10.00,
        TRUE
    ),
    (
        'Winter Warmers',
        '20% off on selected spirits',
        20.00,
        TRUE
    );

INSERT INTO
    "BeverageDiscount" (beverage_id, discount_id)
VALUES (9, 1),
    (10, 2);
