-- =====================================
--    PRODUCT CATEGORY - INSERTS
-- =====================================
INSERT INTO product_category (name, description) VALUES
('Food', 'Healthy and nutritious food for pets'),
('Toys', 'Entertainment toys for all pets'),
('Accessories', 'Collars, leashes, beds and more'),
('Hygiene', 'Shampoos, grooming items, cleaning products');


-- =====================================
--            PRODUCTS
-- =====================================
INSERT INTO products (name, product_description, price, discountedPrice, pictureProduct, brand, id_category) VALUES
('Dog Premium Food', 'High quality food for adult dogs', 25.99, 20.99, 'dog_food.jpg', 'Purina', 1),
('Cat Tuna Pack', 'Tuna-based wet food for cats', 15.50, 13.00, 'cat_tuna.jpg', 'Whiskas', 1),
('Chew Toy Bone', 'Durable rubber bone for dogs', 9.99, NULL, 'toy_bone.jpg', 'Kong', 2),
('Cat Feather Wand', 'Interactive feather toy', 7.99, 6.50, 'feather_wand.jpg', 'Trixie', 2),
('Leather Collar', 'High durability leather collar for dogs', 12.50, 10.00, 'leather_collar.jpg', 'PetSafe', 3),
('Pet Shampoo', 'Soft shampoo for sensitive pet skin', 8.99, NULL, 'shampoo.jpg', 'Beaphar', 4),


('Puppy Starter Food', 'Balanced nutrition for growing puppies', 18.99, 16.99, 'puppy_food.jpg', 'Purina', 1),
('Senior Dog Chicken Mix', 'Soft and nutritious food for senior dogs', 22.50, 19.99, 'senior_dog_food.jpg', 'Royal Canin', 1),
('Cat Salmon Dry Food', 'High-protein salmon-based dry food', 19.99, 17.50, 'cat_salmon.jpg', 'Whiskas', 1),
('Rabbit Veggie Blend', 'Healthy vegetable mix for rabbits', 12.99, 11.50, 'rabbit_veggie.jpg', 'Vitakraft', 1),
('Hamster Nut Mix', 'Crunchy nut and seed mix for hamsters', 6.50, 5.99, 'hamster_nut.jpg', 'Versele-Laga', 1),


('Squeaky Duck Toy', 'Yellow rubber duck that squeaks when pressed', 5.99, 4.99, 'squeaky_duck.jpg', 'Kong', 2),
('Rope Tug Toy', 'Strong rope for tug-of-war games', 8.99, 7.99, 'rope_tug.jpg', 'Trixie', 2),
('Laser Pointer', 'Interactive laser toy for cats', 4.99, NULL, 'laser_pointer.jpg', 'PetSafe', 2),
('Bird Swing', 'Wooden swing for small birds', 9.50, 8.50, 'bird_swing.jpg', 'Penn-Plax', 2),
('Hedgehog Plush Toy', 'Soft plush toy for dogs and cats', 11.99, 9.99, 'hedgehog_plush.jpg', 'ZippyPaws', 2),


('Adjustable Dog Harness', 'Comfortable and adjustable dog harness', 19.99, 17.99, 'dog_harness.jpg', 'Ruffwear', 3),
('Pet Water Fountain', 'Automatic filtered water dispenser', 45.99, 39.99, 'water_fountain.jpg', 'Catit', 3),
('Cat Scratching Post', 'Durable scratching post with sisal rope', 29.99, 27.99, 'scratching_post.jpg', 'Trixie', 3),
('Travel Water Bottle', 'Portable water bottle for outdoor walks', 10.99, 9.49, 'travel_bottle.jpg', 'Kurgo', 3),
('Pet Bed Deluxe', 'Soft and cozy bed for medium-sized pets', 34.99, 29.99, 'deluxe_bed.jpg', 'Ferplast', 3),


('Pet Grooming Brush', 'Anti-shed brush for all fur types', 14.99, 12.99, 'grooming_brush.jpg', 'Furminator', 4),
('Dental Chew Sticks', 'Chew snacks that help clean teeth', 7.99, 6.99, 'dental_chews.jpg', 'Pedigree', 4),
('Cat Litter Sand', 'Clumping sand with odor control', 16.50, 14.99, 'cat_litter.jpg', 'Sanicat', 4),
('Flea Spray', 'Effective spray to prevent fleas', 12.99, 11.50, 'flea_spray.jpg', 'Frontline', 4),
('Shampoo for Long-Hair Dogs', 'Soft shampoo for long-haired dogs', 9.99, NULL, 'longhair_shampoo.jpg', 'Beaphar', 4);


-- =====================================
--                USERS
-- =====================================
INSERT INTO users (name, username, email, password, phone, address, profile_picture, birthdate, country, is_admin) VALUES
('Carlos Ramírez', 'carlosr', 'carlos@example.com', 'pass123', 123456789, '123 Main St', 'carlos.jpg', '1990-05-21', 'Spain', FALSE),
('María López', 'marial', 'maria@example.com', 'pass123', 987654321, 'Calle Sol 45', 'maria.png', '1995-11-03', 'Mexico', FALSE),
('Laura Gómez', 'laurag', 'laura@example.com', 'pass123', 456789123, 'Av. Libertad 88', NULL, '1988-02-15', 'Argentina', FALSE),


('Admin User', 'admin', 'admin@petshop.com', '$2a$10$AdminPasswordHash', 000000000, 'Admin Street 1', NULL, '1980-01-01', 'Spain', TRUE);


-- =====================================
--                CART
-- =====================================
INSERT INTO cart (total_products, total_price, user_id) VALUES
(0, 0.00, 1),
(0, 0.00, 2),
(0, 0.00, 3),
(0, 0.00, 4);  -- cart for admin user


-- =====================================
--              CART ITEMS
-- =====================================

-- User 1
INSERT INTO cart_item (quantity, id_cart, id_product) VALUES
(2, 1, 1),
(1, 1, 3);

-- User 2
INSERT INTO cart_item (quantity, id_cart, id_product) VALUES
(3, 2, 2),
(1, 2, 4);

-- User 3
INSERT INTO cart_item (quantity, id_cart, id_product) VALUES
(1, 3, 5),
(2, 3, 6);