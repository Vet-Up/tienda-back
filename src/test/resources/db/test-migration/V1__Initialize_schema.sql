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
                          stock INT NOT NULL DEFAULT 0,

                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id)
                                  REFERENCES product_category(category_id)
                                  ON DELETE RESTRICT
);

-- =====================================
--           PRODUCT IMAGES
-- =====================================
CREATE TABLE product_images (
                                product_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                picture_product VARCHAR(255) NOT NULL,
                                is_main BOOLEAN DEFAULT FALSE,
                                position INT DEFAULT 0,
                                product_id BIGINT NOT NULL,

                                CONSTRAINT fk_product_images_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES products(product_id)
                                        ON DELETE CASCADE
);

-- =====================================
--                CART
-- =====================================
CREATE TABLE cart (
                      id_cart BIGINT AUTO_INCREMENT PRIMARY KEY,
                      total_products INT DEFAULT 0,
                      total_price DECIMAL(10,2) DEFAULT 0,
                      user_id BIGINT NULL,
                      FOREIGN KEY (user_id)
                          REFERENCES users(id)
                          ON DELETE CASCADE
);

-- =====================================
--           CART ITEM
-- =====================================
CREATE TABLE cart_item (
                           id_cart_item BIGINT AUTO_INCREMENT PRIMARY KEY,
                           quantity INT NOT NULL,
                           id_cart BIGINT NOT NULL,
                           product_id BIGINT NOT NULL,
                           FOREIGN KEY (id_cart)
                               REFERENCES cart(id_cart) ON DELETE CASCADE,
                           FOREIGN KEY (product_id)
                               REFERENCES products(product_id) ON DELETE CASCADE
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
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        order_at TIMESTAMP NULL,
                        address VARCHAR(255),
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
                                 REFERENCES orders(id_order)
                                 ON DELETE CASCADE,

                             FOREIGN KEY (product_id)
                                 REFERENCES products(product_id)
                                 ON DELETE CASCADE
);

-- =====================================
--        PRODUCT REVIEW / RATING
-- =====================================
CREATE TABLE product_review (
                                review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                rating INT NOT NULL,
                                comment TEXT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                user_id BIGINT NOT NULL,
                                product_id BIGINT NOT NULL,

                                CONSTRAINT fk_review_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE,

                                CONSTRAINT fk_review_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES products(product_id)
                                        ON DELETE CASCADE,

                                CONSTRAINT uq_review_user_product
                                    UNIQUE (user_id, product_id),

                                CONSTRAINT chk_review_rating
                                    CHECK (rating BETWEEN 1 AND 5)
);