-- Users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100),
                       phone VARCHAR(20),
                       address VARCHAR(500),
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role VARCHAR(20) NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Categories
CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            description VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Products
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          stock INT NOT NULL DEFAULT 0,
                          image_url VARCHAR(500),
                          category_id BIGINT,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at DATETIME NOT NULL,
                          updated_at DATETIME,
                          FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_product_name ON products(name);
CREATE INDEX idx_product_category ON products(category_id);

-- Carts
CREATE TABLE carts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id BIGINT UNIQUE,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cart_items (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            cart_id BIGINT NOT NULL,
                            product_id BIGINT NOT NULL,
                            quantity INT NOT NULL,
                            FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Orders
CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_number VARCHAR(50) NOT NULL UNIQUE,
                        user_id BIGINT NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        shipping_address VARCHAR(500) NOT NULL,
                        phone VARCHAR(20),
                        note VARCHAR(500),
                        created_at DATETIME NOT NULL,
                        updated_at DATETIME,
                        FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_order_status ON orders(status);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             product_name VARCHAR(200) NOT NULL,
                             product_image VARCHAR(500),
                             price DECIMAL(10,2) NOT NULL,
                             quantity INT NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;