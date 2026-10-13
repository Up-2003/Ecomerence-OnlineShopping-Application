-- Admin user (password: admin123)
INSERT INTO users (username, email, password, full_name, enabled, created_at)
VALUES ('admin', 'admin@ecommerce.com',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa',
        'Administrator', TRUE, NOW());

INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN' FROM users WHERE username = 'admin';
INSERT INTO user_roles (user_id, role)
SELECT id, 'USER' FROM users WHERE username = 'admin';

-- Demo user (password: user123)
INSERT INTO users (username, email, password, full_name, enabled, created_at)
VALUES ('user', 'user@ecommerce.com',
        '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQFdmKQ2R5t8uN.kLQ6n6wNO6dVxaK',
        'Demo User', TRUE, NOW());

INSERT INTO user_roles (user_id, role)
SELECT id, 'USER' FROM users WHERE username = 'user';

-- Categories
INSERT INTO categories (name, description) VALUES
                                               ('Electronics', 'Electronic devices and gadgets'),
                                               ('Clothing', 'Fashion and apparel'),
                                               ('Books', 'Books and literature'),
                                               ('Home & Garden', 'Home improvement and garden supplies');

-- Products
INSERT INTO products (name, description, price, stock, image_url, category_id, created_at, updated_at)
VALUES
    ('iPhone 15 Pro', 'Latest Apple smartphone with A17 chip', 1099.00, 50,
     'https://via.placeholder.com/300x300?text=iPhone', 1, NOW(), NOW()),
    ('Samsung Galaxy S24', 'Samsung flagship with AI features', 999.00, 40,
     'https://via.placeholder.com/300x300?text=Samsung', 1, NOW(), NOW()),
    ('MacBook Pro 14"', 'Apple laptop with M3 chip', 1999.00, 20,
     'https://via.placeholder.com/300x300?text=MacBook', 1, NOW(), NOW()),
    ('Sony WH-1000XM5', 'Noise cancelling headphones', 399.00, 100,
     'https://via.placeholder.com/300x300?text=Sony', 1, NOW(), NOW()),
    ('Nike Air Max', 'Running shoes with air cushion', 149.00, 200,
     'https://via.placeholder.com/300x300?text=Nike', 2, NOW(), NOW()),
    ('Levi''s Jeans', 'Classic blue denim jeans', 89.00, 150,
     'https://via.placeholder.com/300x300?text=Levis', 2, NOW(), NOW()),
    ('Clean Code', 'Robert C. Martin - Best practices', 45.00, 300,
     'https://via.placeholder.com/300x300?text=CleanCode', 3, NOW(), NOW()),
    ('Effective Java', 'Joshua Bloch - Java best practices', 55.00, 250,
     'https://via.placeholder.com/300x300?text=EffectiveJava', 3, NOW(), NOW()),
    ('Coffee Maker', 'Programmable coffee maker', 79.00, 80,
     'https://via.placeholder.com/300x300?text=CoffeeMaker', 4, NOW(), NOW()),
    ('Garden Tool Set', '5-piece garden tool set', 49.00, 120,
     'https://via.placeholder.com/300x300?text=GardenTools', 4, NOW(), NOW());