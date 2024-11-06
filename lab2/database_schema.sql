Table Beverage {
  id int [primary key]
  name varchar(255)
  description varchar(255)
  price decimal(10, 2)
  available_quantity int
  brand varchar(255)
  alcohol_percentage decimal(5, 2)
  created_at timestamp
  updated_at timestamp
  category_id int [ref: > Category.id]
}


Table Category {
  id int [primary key]
  name varchar(255)
  created_at timestamp
}


Table User {
  id int [primary key]
  email varchar(255)
  password_hash text
  created_at timestamp
  updated_at timestamp
  role_id int [ref: > Role.id]
}


Table Employee {
  id int [primary key]
  first_name varchar(255)
  last_name varchar(255)
  salary decimal(10, 2)
  phone char(50)
  position varchar(255)
  email varchar(255)
  created_at timestamp
  updated_at timestamp
}


Table Client {
  id int [primary key]
  name varchar(255)
  email varchar(255)
  created_at timestamp
  updated_at timestamp
}


Table Role {
  id int [primary key]
  name varchar(255)
  created_at timestamp
}


Table Order {
  id int [primary key]
  price decimal(10, 2)
  created_at timestamp
  updated_at timestamp
  client_id int [ref: > Client.id]
}


Table OrderItem {
  id int [primary key]
  beverage_quantity int
  beverage_price decimal(10, 2)
  created_at timestamp
  updated_at timestamp
  beverage_id int [ref: > Beverage.id]
  order_id int [ref: > Order.id]
}


Table Cart {
  id int [primary key]
  price decimal(10, 2)
  created_at timestamp
  updated_at timestamp
  client_id int [ref: - Client.id]
}


Table CartItem {
  id int [primary key]
  beverage_quantity int
  beverage_price decimal(10, 2)
  created_at timestamp
  updated_at timestamp
  beverage_id int [ref: > Beverage.id]
  cart_id int [ref: > Cart.id]
}


Table Review {
  id int [primary key]
  content text
  rating int
  created_at timestamp
  updated_at timestamp
  beverage_id int [ref: > Beverage.id]
  client_id int [ref: > Client.id]
}


Table Discount {
  id int [primary key]
  name varchar(255)
  description varchar(255)
  percent decimal(5, 2)
  is_active boolean
  created_at timestamp
}


Table BeverageDiscount {
  id int [primary key]
  created_at timestamp
  updated_at timestamp
  beverage_id int [ref: <> Beverage.id]
  discount_id int [ref: <> Discount.id]
}
