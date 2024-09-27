Table Beverage {
  id int [primary key]
  name varchar(255)
  description varchar(255)
  price real
  available_quantity int
  brand varchar(255)
  alcohol_percentage real
  category_id int [ref: > Category.id]
}

Table Category {
  id int [primary key]
  name varchar(255)
}

Table User {
  id int [primary key]
  email varchar(255)
  password_hash text
  refresh_token text
  role_id int [ref: > Role.id]
}

Table Employee {
  id int [primary key]
  first_name varchar(255)
  last_name varchar(255)
  salary real
  phone char(50)
  position varchar(255)
  email varchar(255)
}

Table Client {
  id int [primary key]
  name varchar(255)
  email varchar(255)
}

Table Role {
  id int [primary key]
  name varchar(255)
}

Table Order {
  id int [primary key]
  date date
  price real
  client_id int [ref: > Client.id]
}

Table OrderItem {
  id int [primary key]
  beverage_quantity int
  beverage_price real
  beverage_id int [ref: > Beverage.id]
  order_id int [ref: > Order.id]
}

Table Cart {
  id int [primary key]
  price real
  client_id int [ref: - Client.id]
}

Table CartItem {
  id int [primary key]
  beverage_quantity int
  beverage_price real
  beverage_id int [ref: > Beverage.id]
  cart_id int [ref: > Cart.id]
}

Table Review {
  id int [primary key]
  content varchar
  rating int
  date date
  beverage_id int [ref: > Beverage.id]
  client_id int [ref: > Client.id]
}

Table Discount {
  id int [primary key]
  name varchar(255)
  description varchar(255)
  percent real
  is_active boolean
}

Table BeverageDiscount {
  id int [primary key]
  beverage_id int [ref: <> Beverage.id]
  discount_id int [ref: <> Discount.id]
}
