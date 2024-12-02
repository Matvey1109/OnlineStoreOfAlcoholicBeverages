-- Triggers

-- #1
CREATE OR REPLACE FUNCTION update_cart_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE "Cart"
    SET price = COALESCE((
        SELECT SUM(beverage_price * beverage_quantity)
        FROM "CartItem"
        WHERE cart_id = NEW.cart_id
    ), 0)
    WHERE id = NEW.cart_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE
OR REPLACE TRIGGER cart_item_trigger
AFTER INSERT
OR
UPDATE
OR DELETE ON "CartItem" FOR EACH ROW
EXECUTE FUNCTION update_cart_total ();

-- #2
CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE "Order"
    SET price = COALESCE((
        SELECT SUM(beverage_price * beverage_quantity)
        FROM "OrderItem"
        WHERE order_id = NEW.order_id
    ), 0)
    WHERE id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE
OR REPLACE TRIGGER order_item_trigger
AFTER INSERT
OR
UPDATE
OR DELETE ON "OrderItem" FOR EACH ROW
EXECUTE FUNCTION update_order_total ();

-- #3
CREATE OR REPLACE FUNCTION deduct_stock()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE "Beverage"
    SET available_quantity = available_quantity - NEW.beverage_quantity
    WHERE id = NEW.beverage_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE
OR REPLACE TRIGGER stock_deduction_trigger
AFTER INSERT ON "OrderItem" FOR EACH ROW
EXECUTE FUNCTION deduct_stock ();

-- #4
CREATE OR REPLACE FUNCTION validate_discount()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.percent < 0 OR NEW.percent > 100 THEN
        RAISE EXCEPTION 'Discount percent must be between 0 and 100';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE
OR REPLACE TRIGGER discount_validation_trigger BEFORE INSERT
OR
UPDATE ON "Discount" FOR EACH ROW
EXECUTE FUNCTION validate_discount ();

-- #5
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER auto_update_timestamp BEFORE
UPDATE ON "Beverage" FOR EACH ROW
EXECUTE FUNCTION update_timestamp ();
