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
    is_admin BOOLEAN DEFAULT FALSE
);

-- =====================================
--         PRODUCT CATEGORY
-- =====================================
CREATE TABLE product_category (
    id_category BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- =====================================
--               PRODUCT
-- =====================================
CREATE TABLE products (
    id_product BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    product_description TEXT,
    price DECIMAL(10,2) NOT NULL,
    discountedPrice DECIMAL(10,2),
    pictureProduct VARCHAR(255),
    brand VARCHAR(100),
    id_category BIGINT,
    FOREIGN KEY (id_category)
        REFERENCES product_category(id_category)
);

-- =====================================
--                ORDER
-- =====================================
CREATE TABLE cart (
    id_cart BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_products INT DEFAULT 0,
    total_price DECIMAL(10,2) DEFAULT 0,
    user_id BIGINT UNIQUE NULL,
    state INT DEFAULT 0,
    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);

-- =====================================
--              ORDER ITEM
-- =====================================
CREATE TABLE cart_item (
    id_item_cart BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,

    id_cart BIGINT NOT NULL,
    id_product BIGINT NOT NULL,

    FOREIGN KEY (id_cart)
        REFERENCES cart(id_cart) ON DELETE CASCADE,

    FOREIGN KEY (id_product)
        REFERENCES products(id_product)
);
