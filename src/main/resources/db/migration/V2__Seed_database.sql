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
INSERT INTO products
(name, product_description, price, discounted_price, picture_product, brand, category_id, stock)
VALUES
('Dog Premium Food', 'High quality food for adult dogs.', 25.99, 20.99, 'purina-dog-premium-food.png', 'Purina', 1, 150),
('Cat Tuna Pack', 'Tuna-based wet food for cats.', 15.50, 13.00, 'whiskas_humedo_pescados_gatos.jpg', 'Whiskas', 1, 200),
('Chew Toy Bone', 'Durable rubber bone for dogs.', 9.99, NULL, 'chew-toy-bone-kong.jpg', 'Kong', 2, 75),
('Cat Feather Wand', 'Interactive feather toy.', 7.99, 6.50, 'cat-feather-wand-trixie.jpg', 'Trixie', 2, 120),
('Cognac Moni Collar', 'Cognac-tone collar, a touch of sophistication for your dog.', 12.50, 10.00, 'collar-perro-moni-conac-frontal.png', 'Brannipets', 3, 50),
('Pet Shampoo', 'Soft shampoo for sensitive pet skin.', 8.99, NULL, 'pet-shampoo-beaphar.jpg', 'Beaphar', 4, 100),
('Puppy Starter Food', 'Balanced nutrition for growing puppies.', 18.99, 16.99, 'purina-pro-plan-dog-puppy-starter-medium-pollo.png', 'Purina', 1, 80),
('Senior Dog Chicken Mix', 'Soft and nutritious food for senior dogs.', 22.50, 19.99, 'Royal-Canin-Medium-pienso-senior.jpg', 'Royal Canin', 1, 60),
('Cat Salmon Dry Food', 'High-protein salmon-based dry food.', 19.99, 17.50, 'cat-salmon-dry-food-whiskas.jpg', 'Whiskas', 1, 90),
('Rabbit Veggie Blend', 'Healthy vegetable mix for rabbits.', 12.99, 11.50, 'vitakraft-barrita-de-verdura.jpg', 'Vitakraft', 1, 45),
('Hamster Nut Mix', 'Crunchy nut and seed mix for hamsters.', 6.50, 5.99, 'versele-comida-hamster.png', 'Versele-Laga', 1, 110),
('Squeaky Duck Toy', 'Yellow rubber duck that squeaks when pressed.', 5.99, 4.99, 'squeaky-duck-toy-kong.jpg', 'Kong', 2, 200),
('Rope Tug Toy', 'Strong rope for tug-of-war games.', 8.99, 7.99, 'rope-tug-toy-trixie.jpg', 'Trixie', 2, 85),
('Laser Pointer', 'Interactive laser toy for cats.', 4.99, NULL, 'laser-pointer-petsafe.png', 'PetSafe', 2, 150),
('Bird Swing', 'Wooden swing for small birds.', 9.50, 8.50, 'bird-swing-pennplax-4.jpg', 'Penn-Plax', 2, 65),
('Hedgehog Plush Toy', 'Soft plush toy for dogs and cats.', 11.99, 9.99, 'hedgehog-plush-toy-zippypaws.png', 'ZippyPaws', 2, 95),
('Adjustable Dog Harness', 'Comfortable and adjustable dog harness.', 19.99, 17.99, 'adjustable-dog-harness-ruffwear.jpg', 'Ruffwear', 3, 40),
('Pet Water Fountain', 'Automatic filtered water dispenser.', 45.99, 39.99, 'pet-water-fountain-catit.png', 'Catit', 3, 30),
('Cat Scratching Post', 'Durable scratching post with sisal rope.', 29.99, 27.99, 'cat-scratching-post-trixie.jpg', 'Trixie', 3, 55),
('Travel Water Bottle', 'Portable water bottle for outdoor walks.', 10.99, 9.49, 'travel-water-bottle-kurgo.jpg', 'Kurgo', 3, 130),
('Pet Bed Deluxe', 'Soft and cozy bed for medium-sized pets.', 34.99, 29.99, 'pet-bed-deluxe-ferplast.png', 'Ferplast', 3, 25),
('Pet Grooming Brush', 'Anti-shed brush for all fur types.', 14.99, 12.99, 'pet-grooming-brush.ong.jpg', 'Furminator', 4, 70),
('Dental Chew Sticks', 'Chew snacks that help clean teeth.', 7.99, 6.99, 'dental-chew-sticks-pedigree.jpg', 'Pedigree', 4, 180),
('Cat Litter Sand', 'Clumping sand with odor control.', 16.50, 14.99, 'cat-litter-sand-sanicat.png', 'Sanicat', 4, 100),
('Flea Spray', 'Effective spray to prevent fleas.', 12.99, 11.50, 'flea-spray-frontline.png', 'Frontline', 4, 75),
('Shampoo Hair Dogs', 'Soft shampoo for long-haired dogs.', 9.99, NULL, 'shampoo-long-hair-dogs-beaphar.png', 'Beaphar', 4, 90);


-- =====================================
--                USERS
-- =====================================

-- adminpass juanpass mariapass carlospass anapass pedrospass lauragpass miguelfpass sofiarpass diegorpass elenatpass adminpass2
INSERT INTO users (name, username, email, password, phone, address, profile_picture, birthdate, country, is_admin)
VALUES
    ('Admin User', 'admin', 'admin@vetup.com', '$2a$12$MxHbH00a6bDvv4DjiCmhKe3UwmH5Vn5aWzpXDtC/uq/LMRkcePNoe', 123456789, 'Calle Admin 1', NULL, '1990-01-01', 'España', 'ADMIN'),
        ('Juan Pérez', 'juanp', 'juanp@gmail.com', '$2a$12$qHuycfsyeUJ1PtGfdcb0I.wwkklUuYYz5ay2HC1sjpdx5iBzwgXt6', 654123987, 'Avenida Siempre Viva 742', NULL, '1995-05-15', 'España', 'CUSTOMER'),
        ('María Gómez', 'mariag', 'mariag@gmail.com', '$2a$12$e/IGrg0J0G9f0l/E/JBJAu/apz4G6J8FRC5mD1iiJaGDUiu9J1Cs.', 612334455, 'Gran Vía 12', NULL, '1998-09-20', 'España', 'CUSTOMER'),
        ('Carlos López', 'carlosl', 'carlosl@gmail.com', '$2a$12$KffWVPnCNPHsUtE7EY9y6.a2qXalu0IFEKi3fH4C1/F5u2E.EKNXO', 666777888, 'Calle Mayor 25', NULL, '1992-03-10', 'España', 'CUSTOMER'),
        ('Ana Martínez', 'anam', 'anam@gmail.com', '$2a$12$SpE.Vvn3.RuHQVrRpOxO8u6197SDu.rFdUcEKoMKsyywItQXO.7Y6', 655443322, 'Paseo del Prado 8', NULL, '1988-07-22', 'España', 'CUSTOMER'),
        ('Pedro Solís', 'pedros', 'pedros@gmail.com', '$2a$12$UUa/1aHdqEG6v5EdDDeUvO9kbgRI8yxo2jsVao/BRvGqYOgiHpC8W', 699887766, 'Avenida Diagonal 120', NULL, '1990-11-05', 'España', 'CUSTOMER'),
        ('Laura García', 'laurag', 'laurag@gmail.com', '$2a$12$NzjZcRDlqyYfKjnyZ3FxfeM.JFxbVg9UB2a3rQywvhsp1vTb5thxm', 611223344, 'Calle Luna 15', NULL, '1997-02-14', 'España', 'CUSTOMER'),
        ('Miguel Fernández', 'miguelf', 'miguelf@gmail.com', '$2a$12$.YD1noihtgamoBGaOuJp/uK3V8DHqWsCACkNwyEzL6u9B1EnY3KKG', 644556677, 'Plaza España 3', NULL, '1985-09-30', 'España', 'CUSTOMER'),
        ('Sofía Rodríguez', 'sofiar', 'sofiar@gmail.com', '$2a$12$HELDd8bOGI6BIr0EtzwVMuayEzOGbPwOFEjyg1lPa2my3WJ7hij7m', 677889900, 'Calle Sol 42', NULL, '1993-06-18', 'España', 'CUSTOMER'),
        ('Diego Ruiz', 'diegor', 'diegor@gmail.com', '$2a$12$L3kj0EKk6ujAo.tME0zX6.2BYHHwWGBMSko5Zvm6CJB6kMRC/o7G.', 622334455, 'Avenida Libertad 77', NULL, '1991-12-25', 'España', 'CUSTOMER'),
        ('Elena Torres', 'elenat', 'elenat@gmail.com', '$2a$12$dg3ywi9QtDAFYQ7/sdVzQu6lmoXlrvXlpeRbhl1wtQurAZ/WhTlm2', 688990011, 'Calle Flores 9', NULL, '1996-04-08', 'España', 'CUSTOMER'),
        ('Admin Secundario', 'admin2', 'admin2@vetup.com', '$2a$12$lZH4UVftaZcZG/r/muz95.ka4ZsQ.onobUZeWtRIMKiSArCH/hUA2', 600111222, 'Calle Admin 2', NULL, '1988-08-15', 'España', 'ADMIN');

-- =====================================
--                CART
-- =====================================
INSERT INTO cart (total_products, total_price, user_id)
VALUES
    (3, 52.48, 2),  -- id_cart = 1 (Juan Pérez)
    (2, 29.98, 3),  -- id_cart = 2 (María Gómez)
    (0, 0.00, 4),   -- id_cart = 3 (Carlos López - carrito vacío)
    (1, 22.50, 5);  -- id_cart = 4 (Ana Martínez)

-- =====================================
--             CART ITEM
-- =====================================
-- Para id_cart = 1 (Juan Pérez)
INSERT INTO cart_item (quantity, id_cart, product_id)
VALUES
    (1, 1, 1),  -- Dog Premium Food
    (2, 1, 3),  -- Chew Toy Bone
    (1, 1, 6);  -- Pet Shampoo

-- Para id_cart = 2 (María Gómez)
INSERT INTO cart_item (quantity, id_cart, product_id)
VALUES
    (1, 2, 2),  -- Cat Tuna Pack
    (1, 2, 4);  -- Cat Feather Wand

-- Para id_cart = 4 (Ana Martínez)
INSERT INTO cart_item (quantity, id_cart, product_id)
VALUES
    (1, 4, 8);  -- Senior Dog Chicken Mix

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

-- =====================================
--         PRODUCT REVIEWS - INSERTS
-- =====================================
INSERT INTO product_review (rating, comment, user_id, product_id) VALUES
-- 1 Dog Premium Food (9)
(5, 'Excelente calidad', 2, 1),
(4, 'A mi perro le encanta', 3, 1),
(5, 'Muy nutritivo', 4, 1),
(3, 'Algo caro', 5, 1),
(4, 'Buen olor', 6, 1),
(5, 'Compra habitual', 7, 1),
(2, 'No le sentó bien a mi perro', 8, 1),
(4, 'Buen producto', 9, 1),
(5, 'Muy recomendable', 10, 1),

-- 2 Cat Tuna Pack (7)
(5, 'Mi gato lo devora', 3, 2),
(4, 'Buen sabor', 4, 2),
(3, 'Olor fuerte', 5, 2),
(5, 'Muy jugoso', 6, 2),
(2, 'A mi gato no le gustó', 7, 2),
(4, 'Buena textura', 8, 2),
(5, 'Repetiré', 9, 2),

-- 3 Chew Toy Bone (5)
(5, 'Muy resistente', 2, 3),
(4, 'Buen tamaño', 3, 3),
(3, 'Algo duro', 4, 3),
(2, 'Se rompió rápido', 5, 3),
(4, 'Cumple su función', 6, 3),

-- 4 Cat Feather Wand (6)
(5, 'Diversión asegurada', 7, 4),
(4, 'A mi gato le encanta', 8, 4),
(3, 'Algo frágil', 9, 4),
(5, 'Muy entretenido', 10, 4),
(2, 'Duró poco', 11, 4),
(4, 'Buen precio', 2, 4),

-- 5 Cognac Moni Collar (4)
(5, 'Muy elegante', 3, 5),
(4, 'Buena calidad', 4, 5),
(3, 'Tallaje justo', 5, 5),
(4, 'Bonito diseño', 6, 5),

-- 7 Puppy Starter Food (8)
(5, 'Ideal para cachorros', 2, 7),
(4, 'Buen tamaño de croqueta', 3, 7),
(5, 'Muy completo', 4, 7),
(3, 'Algo caro', 5, 7),
(4, 'Buena digestión', 6, 7),
(5, 'Mi cachorro feliz', 7, 7),
(2, 'No le gustó', 8, 7),
(4, 'Recomendable', 9, 7),

-- 8 Senior Dog Chicken Mix (6)
(5, 'Perfecto para perros mayores', 10, 8),
(4, 'Textura adecuada', 11, 8),
(3, 'Normal', 2, 8),
(4, 'Buen sabor', 3, 8),
(2, 'A mi perro no le gustó', 4, 8),
(5, 'Muy bueno', 5, 8),

-- 9 Cat Salmon Dry Food (7)
(5, 'Pelaje más brillante', 6, 9),
(4, 'Buen sabor', 7, 9),
(3, 'Croquetas grandes', 8, 9),
(5, 'Muy nutritivo', 9, 9),
(2, 'No le gusta', 10, 9),
(4, 'Buena calidad', 11, 9),
(5, 'Compra habitual', 2, 9),

-- 10 Rabbit Veggie Blend (4)
(5, 'A mi conejo le encanta', 3, 10),
(4, 'Ingredientes naturales', 4, 10),
(3, 'Algo seco', 5, 10),
(2, 'No le gustó', 6, 10),

-- 11 Hamster Nut Mix (6)
(5, 'Muy completo', 7, 11),
(4, 'Buen mix', 8, 11),
(3, 'Normal', 9, 11),
(2, 'Muchas semillas duras', 10, 11),
(4, 'Buen precio', 11, 11),
(5, 'Le encanta', 2, 11),

-- 12 Squeaky Duck Toy (8)
(5, 'Muy divertido', 3, 12),
(4, 'Hace mucho ruido', 4, 12),
(3, 'Normal', 5, 12),
(2, 'Se rompió', 6, 12),
(5, 'A mi perro le flipa', 7, 12),
(4, 'Buen juguete', 8, 12),
(1, 'Muy mala calidad', 9, 12),
(5, 'Repetiría', 10, 12),

-- 13 Rope Tug Toy (4)
(5, 'Muy resistente', 11, 13),
(4, 'Ideal para juegos', 2, 13),
(3, 'Algo corto', 3, 13),
(2, 'No duró mucho', 4, 13),

-- 14 Laser Pointer (6)
(5, 'Horas de diversión', 5, 14),
(4, 'Funciona bien', 6, 14),
(3, 'Normal', 7, 14),
(2, 'Pilas duran poco', 8, 14),
(1, 'Se estropeó rápido', 9, 14),
(5, 'Muy entretenido', 10, 14),

-- 15 Bird Swing (3)
(5, 'A mi pájaro le gusta', 11, 15),
(4, 'Buen acabado', 2, 15),
(3, 'Normal', 3, 15),

-- 16 Hedgehog Plush Toy (5)
(5, 'Muy suave', 4, 16),
(4, 'Buen acabado', 5, 16),
(3, 'Algo pequeño', 6, 16),
(2, 'Se rompió pronto', 7, 16),
(5, 'Perfecto para dormir', 8, 16),

-- 17 Adjustable Dog Harness (6)
(5, 'Muy cómodo', 9, 17),
(4, 'Buen ajuste', 10, 17),
(3, 'Algo caro', 11, 17),
(2, 'Talla pequeña', 2, 17),
(4, 'Buen material', 3, 17),
(5, 'Muy seguro', 4, 17),

-- 18 Pet Water Fountain (7)
(5, 'Muy silenciosa', 5, 18),
(4, 'Buen diseño', 6, 18),
(3, 'Normal', 7, 18),
(2, 'Difícil de limpiar', 8, 18),
(1, 'Dejó de funcionar', 9, 18),
(5, 'A mi gato le encanta', 10, 18),
(4, 'Buena compra', 11, 18),

-- 19 Cat Scratching Post (5)
(5, 'Muy resistente', 2, 19),
(4, 'Buen tamaño', 3, 19),
(3, 'Normal', 4, 19),
(2, 'Se mueve mucho', 5, 19),
(5, 'A mi gato le encanta', 6, 19),

-- 20 Travel Water Bottle (4)
(5, 'Muy práctica', 7, 20),
(4, 'Ideal para paseos', 8, 20),
(3, 'Normal', 9, 20),
(2, 'Gotea un poco', 10, 20),

-- 21 Pet Bed Deluxe (8)
(5, 'Muy cómoda', 11, 21),
(4, 'Buen tamaño', 2, 21),
(5, 'A mi perro le encanta', 3, 21),
(3, 'Algo cara', 4, 21),
(2, 'Se hunde rápido', 5, 21),
(4, 'Buen diseño', 6, 21),
(1, 'Muy mala calidad', 7, 21),
(5, 'Repetiría', 8, 21),

-- 22 Pet Grooming Brush (5)
(5, 'Quita mucho pelo', 9, 22),
(4, 'Buen agarre', 10, 22),
(3, 'Normal', 11, 22),
(2, 'No me convence', 2, 22),
(4, 'Cumple su función', 3, 22),

-- 23 Dental Chew Sticks (6)
(5, 'Le encantan', 4, 23),
(4, 'Buen olor', 5, 23),
(3, 'Normal', 6, 23),
(2, 'Muy duros', 7, 23),
(4, 'Buen precio', 8, 23),
(5, 'Compra habitual', 9, 23),

-- 24 Cat Litter Sand (7)
(5, 'Controla bien el olor', 10, 24),
(4, 'Buena absorción', 11, 24),
(3, 'Normal', 2, 24),
(2, 'Hace polvo', 3, 24),
(1, 'Muy mala', 4, 24),
(5, 'Repetiré', 5, 24),
(4, 'Buen producto', 6, 24),

-- 25 Flea Spray (4)
(5, 'Funciona bien', 7, 25),
(4, 'Efectivo', 8, 25),
(2, 'Olor fuerte', 9, 25),
(3, 'Normal', 10, 25),

-- 26 Shampoo Hair Dogs (5)
(5, 'Pelo brillante', 11, 26),
(4, 'Buen aroma', 2, 26),
(3, 'Normal', 3, 26),
(2, 'No vi cambios', 4, 26),
(1, 'Muy malo', 5, 26);

-- =====================================
--        PRODUCT IMAGES - INSERTS
-- =====================================
INSERT INTO product_images (picture_product, is_main, position, product_id) VALUES
('purina-dog-premium-food.png', TRUE, 1, 1),
('purina-dog-premium-food-side.png', FALSE, 2, 1),
('purina-dog-premium-food-back.png', FALSE, 3, 1),

('whiskas_humedo_pescados_gatos.jpg', TRUE, 1, 2),
('whiskas_tuna_side.jpg', FALSE, 2, 2);

