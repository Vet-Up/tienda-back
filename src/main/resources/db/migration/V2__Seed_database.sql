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
INSERT INTO products (name, product_description, price, discounted_price, picture_product, brand, category_id) VALUES
                                                                                                                   ('Dog Premium Food', 'High quality food for adult dogs', 25.99, 20.99, 'purina-dog-premium-food.png', 'Purina', 1),
                                                                                                                   ('Cat Tuna Pack', 'Tuna-based wet food for cats', 15.50, 13.00, 'whiskas_humedo_pescados_gatos.jpg', 'Whiskas', 1),
                                                                                                                   ('Chew Toy Bone', 'Durable rubber bone for dogs', 9.99, NULL, 'chew-toy-bone-kong.jpg', 'Kong', 2),
                                                                                                                   ('Cat Feather Wand', 'Interactive feather toy', 7.99, 6.50, 'cat-feather-wand-trixie.jpg', 'Trixie', 2),
                                                                                                                   ('Cognac Moni Collar', 'Cognac-tone collar, a touch of sophistication for your dog', 12.50, 10.00, 'collar-perro-moni-conac-frontal.png', 'Brannipets', 3),
                                                                                                                   ('Pet Shampoo', 'Soft shampoo for sensitive pet skin', 8.99, NULL, 'pet-shampoo-beaphar.jpg', 'Beaphar', 4),
                                                                                                                   ('Puppy Starter Food', 'Balanced nutrition for growing puppies', 18.99, 16.99, 'purina-pro-plan-dog-puppy-starter-medium-pollo.png', 'Purina', 1),
                                                                                                                   ('Senior Dog Chicken Mix', 'Soft and nutritious food for senior dogs', 22.50, 19.99, 'Royal-Canin-Medium-pienso-senior.jpg', 'Royal Canin', 1),
                                                                                                                   ('Cat Salmon Dry Food', 'High-protein salmon-based dry food', 19.99, 17.50, 'cat-salmon-dry-food-whiskas.jpg', 'Whiskas', 1),
                                                                                                                   ('Rabbit Veggie Blend', 'Healthy vegetable mix for rabbits', 12.99, 11.50, 'vitakraft-barrita-de-verdura.jpg', 'Vitakraft', 1),
                                                                                                                   ('Hamster Nut Mix', 'Crunchy nut and seed mix for hamsters', 6.50, 5.99, 'versele-comida-hamster.png', 'Versele-Laga', 1),
                                                                                                                   ('Squeaky Duck Toy', 'Yellow rubber duck that squeaks when pressed', 5.99, 4.99, 'squeaky-duck-toy-kong.jpg', 'Kong', 2),
                                                                                                                   ('Rope Tug Toy', 'Strong rope for tug-of-war games', 8.99, 7.99, 'rope-tug-toy-trixie.jpg', 'Trixie', 2),
                                                                                                                   ('Laser Pointer', 'Interactive laser toy for cats', 4.99, NULL, 'laser-pointer-petsafe.png', 'PetSafe', 2),
                                                                                                                   ('Bird Swing', 'Wooden swing for small birds', 9.50, 8.50, 'bird-swing-pennplax-4.jpg', 'Penn-Plax', 2),
                                                                                                                   ('Hedgehog Plush Toy', 'Soft plush toy for dogs and cats', 11.99, 9.99, 'hedgehog-plush-toy-zippypaws.png', 'ZippyPaws', 2),
                                                                                                                   ('Adjustable Dog Harness', 'Comfortable and adjustable dog harness', 19.99, 17.99, 'adjustable-dog-harness-ruffwear.jpg', 'Ruffwear', 3),
                                                                                                                   ('Pet Water Fountain', 'Automatic filtered water dispenser', 45.99, 39.99, 'pet-water-fountain-catit.png', 'Catit', 3),
                                                                                                                   ('Cat Scratching Post', 'Durable scratching post with sisal rope', 29.99, 27.99, 'cat-scratching-post-trixie.jpg', 'Trixie', 3),
                                                                                                                   ('Travel Water Bottle', 'Portable water bottle for outdoor walks', 10.99, 9.49, 'travel-water-bottle-kurgo.jpg', 'Kurgo', 3),
                                                                                                                   ('Pet Bed Deluxe', 'Soft and cozy bed for medium-sized pets', 34.99, 29.99, 'pet-bed-deluxe-ferplast.png', 'Ferplast', 3),
                                                                                                                   ('Pet Grooming Brush', 'Anti-shed brush for all fur types', 14.99, 12.99, 'pet-grooming-brush.ong.jpg', 'Furminator', 4),
                                                                                                                   ('Dental Chew Sticks', 'Chew snacks that help clean teeth', 7.99, 6.99, 'dental-chew-sticks-pedigree.jpg', 'Pedigree', 4),
                                                                                                                   ('Cat Litter Sand', 'Clumping sand with odor control', 16.50, 14.99, 'cat-litter-sand-sanicat.png', 'Sanicat', 4),
                                                                                                                   ('Flea Spray', 'Effective spray to prevent fleas', 12.99, 11.50, 'flea-spray-frontline.png', 'Frontline', 4),
                                                                                                                   ('Shampoo Hair Dogs', 'Soft shampoo for long-haired dogs', 9.99, NULL, 'shampoo-long-hair-dogs-beaphar.png', 'Beaphar', 4);

-- =====================================
--                USERS
-- =====================================
INSERT INTO users (name, username, email, password, phone, address, profile_picture, birthdate, country, is_admin)
VALUES
    ('Admin User', 'admin', 'admin@vetup.com', '$2a$12$MxHbH00a6bDvv4DjiCmhKe3UwmH5Vn5aWzpXDtC/uq/LMRkcePNoe', 123456789, 'Calle Admin 1', NULL, '1990-01-01', 'España', 'ADMIN'),
    ('Juan Pérez', 'juanp', 'juanp@gmail.com', '$2a$12$wOEtCkY2IaFwjgW/u8HEoOmWsHezFzTOm2HSMUEN2PpHbG4zAu95G', 654123987, 'Avenida Siempre Viva 742', NULL, '1995-05-15', 'España', 'CUSTOMER'),
    ('María Gómez', 'mariag', 'mariag@gmail.com', 'mariag@gmail.com', 612334455, 'Gran Vía 12', NULL, '1998-09-20', 'España', 'CUSTOMER');

-- =====================================
--               ORDERS
-- =====================================
-- Insertando primero los pedidos para que existan los id_order
INSERT INTO orders (total_products, total_price, user_id, state)
VALUES
    (2, 39.98, 2, 'ORDER'),  -- id_order = 1
    (1, 29.99, 3, 'CART'),   -- id_order = 2
    (3, 49.98, 3, 'ORDER');  -- id_order = 3

-- =====================================
--             ORDER ITEMS
-- =====================================
-- Para id_order = 1
INSERT INTO orders_item (quantity, id_order, product_id)
VALUES
    (1, 1, 1),
    (1, 1, 2);

-- Para id_order = 2
INSERT INTO orders_item (quantity, id_order, product_id)
VALUES
    (1, 2, 1),
    (3, 2, 2),
    (1, 2, 4);

-- Para id_order = 3
INSERT INTO orders_item (quantity, id_order, product_id)
VALUES
    (1, 3, 5),
    (2, 3, 6);
