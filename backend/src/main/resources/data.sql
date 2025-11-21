---------------------------------------------------
-- USERS
---------------------------------------------------
INSERT INTO USERS (username, hashed_password, email, is_admin)
VALUES
('admin_user', 'hashed_pw_123', 'admin@example.com', TRUE);

---------------------------------------------------
-- PRODUCTS (Instruments, Books, Electronics)
---------------------------------------------------

-- INSTRUMENTS
INSERT INTO PRODUCTS (name, sku, product_type, description)
VALUES
('Acoustic Guitar', 'PRD-A1001', 'STRING', 'A full-size acoustic guitar with spruce top.'),
('Electric Guitar', 'PRD-A2001', 'STRING', 'A classic electric guitar with maple neck.'),
('Trumpet', 'PRD-B2001', 'BRASS', 'Beginner-friendly brass trumpet with case.'),
('Trombone', 'PRD-B2002', 'BRASS', 'Standard student trombone.'),
('Snare Drum', 'PRD-P3001', 'PERCUSSION', '14-inch steel snare drum.'),
('Bass Drum', 'PRD-P3002', 'PERCUSSION', 'Large marching bass drum.');

-- BOOKS
INSERT INTO PRODUCTS (name, sku, product_type, description)
VALUES
('The Haunted Manor', 'PRD-H4001', 'HORROR', 'A chilling ghost story set in an abandoned estate.'),
('Shadow of the Mountain', 'PRD-H4002', 'HORROR', 'A chilling horror novel set in a remote village.'),
('Romantic Dawn', 'PRD-R5001', 'ROMANCE', 'A charming love story about unexpected connections.'),
('Love in Autumn', 'PRD-R5002', 'ROMANCE', 'A heartfelt romance story about second chances.'),
('Deep Space Mysteries', 'PRD-M6001', 'MYSTERY', 'A gripping sci-fi mystery adventure.'),
('Detective Noir', 'PRD-M6002', 'MYSTERY', 'Classic detective mystery story.');

-- ELECTRONICS
INSERT INTO PRODUCTS (name, sku, product_type, description)
VALUES
('Gaming Laptop X15', 'PRD-L6001', 'LAPTOPS', 'High-performance gaming laptop with RTX graphics.'),
('UltraBook X12', 'PRD-L6002', 'LAPTOPS', 'A lightweight ultrabook with 16GB RAM.'),
('Smartphone Z', 'PRD-S7001', 'SMARTPHONES', 'Flagship smartphone with OLED display.'),
('GalaxyNova Pro', 'PRD-S7002', 'SMARTPHONES', 'High-end smartphone with advanced camera.'),
('PlayBox Series Z', 'PRD-C8001', 'GAME_CONSOLES', 'Next-gen gaming console with 4K support.'),
('GameSphere One', 'PRD-C8002', 'GAME_CONSOLES', 'Compact gaming console with motion controls.');

---------------------------------------------------
-- WAREHOUSES (6 total)
---------------------------------------------------

INSERT INTO WAREHOUSES (user_id, name, warehouse_type, capacity, location)
VALUES
(1, 'Harmony Instruments Depot', 'INSTRUMENTS', 500, 'Dallas, TX'),
(1, 'Golden Leaf Book Storage', 'BOOKS', 800, 'Austin, TX'),
(1, 'ElectroHub Storage', 'ELECTRONICS', 300, 'Houston, TX'),
(1, 'String & Brass Depot', 'INSTRUMENTS', 500, 'Nashville, TN'),
(1, 'Literary Haven Storage', 'BOOKS', 1200, 'Boston, MA'),
(1, 'TechHub Distribution', 'ELECTRONICS', 800, 'Seattle, WA'),
(1, 'Percussion Palace', 'INSTRUMENTS', 650, 'Denver, CO'),
(1, 'Woodwind World Storage', 'INSTRUMENTS', 700, 'Chicago, IL'),
(1, 'Epic Tales Book Depot', 'BOOKS', 900, 'Portland, OR'),
(1, 'Quantum Electronics Vault', 'ELECTRONICS', 500, 'San Francisco, CA'),
(1, 'Digital Horizons Tech Center', 'ELECTRONICS', 950, 'New York, NY');

---------------------------------------------------
-- SECTIONS (ENUM-style names)
---------------------------------------------------

-- WAREHOUSE 1 (INSTRUMENTS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(1, 'WOODWIND'),
(1, 'BRASS'),
(1, 'STRING'),
(1, 'PERCUSSION');

-- WAREHOUSE 2 (BOOKS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(2, 'DRAMA'),
(2, 'ROMANCE'),
(2, 'NONFICTION'),
(2, 'HORROR'),
(2, 'MYSTERY');

-- WAREHOUSE 3 (ELECTRONICS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(3, 'LAPTOPS'),
(3, 'SMARTPHONES'),
(3, 'GAME_CONSOLES');

-- WAREHOUSE 4 (INSTRUMENTS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(4, 'WOODWIND'),
(4, 'BRASS'),
(4, 'STRING'),
(4, 'PERCUSSION');


-- WAREHOUSE 5 (BOOKS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(5, 'DRAMA'),
(5, 'ROMANCE'),
(5, 'NONFICTION'),
(5, 'HORROR'),
(5, 'MYSTERY');

-- WAREHOUSE 6 (ELECTRONICS)
INSERT INTO SECTIONS (warehouse_id, name) VALUES
(6, 'LAPTOPS'),
(6, 'SMARTPHONES'),
(6, 'GAME_CONSOLES');

---------------------------------------------------
-- SECTION-PRODUCT RELATIONS
-- (Products auto-match section by product_type)
---------------------------------------------------

-- INSTRUMENTS (warehouse 1 sections: 1,2,3)
INSERT INTO SECTIONS_PRODUCTS (section_id, product_id, quantity) VALUES
(3, 1, 20),  -- STRING -> Acoustic Guitar
(3, 2, 15),  -- STRING -> Electric Guitar
(2, 3, 18),  -- BRASS -> Trumpet
(2, 4, 12),  -- BRASS -> Trombone
(4, 5, 10),  -- PERCUSSION -> Snare Drum
(4, 6, 7);   -- PERCUSSION -> Bass Drum

-- BOOKS (warehouse 2 sections: 4,5,6)
INSERT INTO SECTIONS_PRODUCTS (section_id, product_id, quantity) VALUES
(8, 7, 40),  -- HORROR
(8, 8, 30),  
(6, 9, 50),  -- ROMANCE
(6, 10, 45),
(9, 11, 33), -- MYSTERY
(9, 12, 29);

-- ELECTRONICS (warehouse 3 sections: 7,8,9)
INSERT INTO SECTIONS_PRODUCTS (section_id, product_id, quantity) VALUES
(10, 13, 12),  -- LAPTOPS
(10, 14, 9),
(11, 15, 25),  -- SMARTPHONES
(11, 16, 22),
(12, 17, 10),  -- GAME_CONSOLES
(12, 18, 7);

---------------------------------------------------
-- RESET SEQUENCES
---------------------------------------------------

SELECT setval('warehouses_id_seq', (SELECT MAX(id) FROM warehouses));
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('sections_id_seq', (SELECT MAX(id) FROM sections));
