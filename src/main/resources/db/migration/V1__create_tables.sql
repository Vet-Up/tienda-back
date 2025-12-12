-- =====================================
--                USER
-- =====================================
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(120) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone INT,
                       address VARCHAR(255),
                       profile_picture VARCHAR(255),
                       birthdate DATE,
                       country VARCHAR(60),
                       is_admin ENUM('ADMIN','CUSTOMER') DEFAULT 'CUSTOMER'
);

-- =====================================
--         PRODUCT CATEGORY
-- =====================================
CREATE TABLE product_category (
                                  category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(100) NOT NULL,
                                  description VARCHAR(255)
);

-- =====================================
--               PRODUCT
-- =====================================
CREATE TABLE products (
                          product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          product_description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          discounted_price DECIMAL(10,2),
                          picture_product VARCHAR(255),
                          brand VARCHAR(100),
                          category_id BIGINT,
                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id)
                                  REFERENCES product_category(category_id)
                                  ON DELETE RESTRICT
);

-- =====================================
--                ORDER
-- =====================================
CREATE TABLE orders (
                        id_order BIGINT AUTO_INCREMENT PRIMARY KEY,
                        total_products INT DEFAULT 0,
                        total_price DECIMAL(10,2) DEFAULT 0,
                        user_id BIGINT NULL,
                        state ENUM('CART','ORDER') DEFAULT 'CART',
                        FOREIGN KEY (user_id)
                            REFERENCES users(id)
                            ON DELETE SET NULL
);

-- =====================================
--              ORDER ITEM
-- =====================================
CREATE TABLE orders_item (
                             id_item_order BIGINT AUTO_INCREMENT PRIMARY KEY,
                             quantity INT NOT NULL,

                             id_order BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,

                             FOREIGN KEY (id_order)
                                 REFERENCES orders(id_order) ON DELETE CASCADE,

                             FOREIGN KEY (product_id)
                                 REFERENCES products(product_id)
);