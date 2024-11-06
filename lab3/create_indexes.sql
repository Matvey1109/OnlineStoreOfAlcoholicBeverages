-- Create indexes for fast searching

CREATE INDEX IF NOT EXISTS idx_user_email ON "User" (email);

CREATE INDEX IF NOT EXISTS idx_employee_email ON "Employee" (email);

CREATE INDEX IF NOT EXISTS idx_client_email ON "Client" (email);

CREATE INDEX IF NOT EXISTS idx_order_client_id ON "Order" (client_id);

CREATE INDEX IF NOT EXISTS idx_order_item_beverage_id ON "OrderItem" (beverage_id);

CREATE INDEX IF NOT EXISTS idx_order_item_order_id ON "OrderItem" (order_id);

CREATE INDEX IF NOT EXISTS idx_cart_client_id ON "Cart" (client_id);

CREATE INDEX IF NOT EXISTS idx_cart_item_beverage_id ON "CartItem" (beverage_id);

CREATE INDEX IF NOT EXISTS idx_cart_item_cart_id ON "CartItem" (cart_id);

CREATE INDEX IF NOT EXISTS idx_review_beverage_id ON "Review" (beverage_id);

CREATE INDEX IF NOT EXISTS idx_discount_name ON "Discount" (name);
