-- Inserting data into category table
INSERT INTO category (id, description, name) VALUES
    (nextval('category_seq'), 'Electronics devices and gadgets', 'Electronics'),
    (nextval('category_seq'), 'Books of various genres and authors', 'Books'),
    (nextval('category_seq'), 'Household furniture and fittings', 'Furniture'),
    (nextval('category_seq'), 'Clothing and accessories', 'Clothing'),
    (nextval('category_seq'), 'Sports equipment and gear', 'Sports');

-- Inserting more data into product table for Electronics category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Laptop with 16GB RAM', 'Laptop', 50, 999.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Noise-cancelling headphones', 'Headphones', 75, 199.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), '4K Ultra HD Smart TV', 'Smart TV', 30, 499.99, (SELECT id FROM category WHERE name = 'Electronics'));

-- Inserting more data into product table for Books category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Science fiction novel', 'Sci-Fi Novel', 40, 24.99, (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Cookbook with 100 recipes', 'Cookbook', 60, 29.99, (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'Biography of a famous scientist', 'Biography', 30, 14.99, (SELECT id FROM category WHERE name = 'Books'));

-- Inserting more data into product table for Furniture category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Wooden dining table', 'Dining Table', 10, 299.99, (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Leather sofa set', 'Sofa', 5, 899.99, (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'King-size bed frame', 'Bed Frame', 15, 499.99, (SELECT id FROM category WHERE name = 'Furniture'));

-- Inserting data into product table for Clothing category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Men\ denim jeans', 'Denim Jeans', 100, 39.99, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Women\ summer dress', 'Summer Dress', 80, 49.99, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Unisex cotton t-shirt', 'T-shirt', 200, 19.99, (SELECT id FROM category WHERE name = 'Clothing'));

-- Inserting data into product table for Sports category
INSERT INTO product (id, description, name, available_quantity, price, category_id) VALUES
    (nextval('product_seq'), 'Mountain bike', 'Bike', 20, 299.99, (SELECT id FROM category WHERE name = 'Sports')),
    (nextval('product_seq'), 'Yoga mat', 'Yoga Mat', 150, 29.99, (SELECT id FROM category WHERE name = 'Sports')),
    (nextval('product_seq'), 'Tennis racket', 'Tennis Racket', 60, 89.99, (SELECT id FROM category WHERE name = 'Sports'));