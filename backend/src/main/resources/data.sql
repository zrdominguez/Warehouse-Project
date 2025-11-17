-- USERS sample data
insert into USERS (id, username, hashed_password, email, is_admin) values (1, 'ctutsell0', '$2a$04$PnlhtYHK2JIsLnsy1PZi.e/Pk9JIxRn4ehmRB5rpKbrVZsR/Z.k32', 'hchesson0@kickstarter.com', true);

-- PRODUCTS sample data
insert into PRODUCTS (id, name, sku, product_type, description, created_at, updated_at) values (1, 'Lemon Sorbet', 'PRD-WIM26R', 'BRASS', 'Refreshing sorbet with a zesty lemon flavor.', '8/15/2023', '8/25/2020');
insert into PRODUCTS (id, name, sku, product_type, description, created_at, updated_at) values (2, 'Microwave Popcorn Maker', 'PRD-KX1I7X', 'STRING', 'Healthier way to make popcorn in the microwave without oil.', '5/3/2017', '6/8/2027');
insert into PRODUCTS (id, name, sku, product_type, description, created_at, updated_at) values (3, 'Peanut Butter Chocolate Clusters', 'PRD-YSY6BK', 'PERCUSSION', 'Delicious clusters of peanuts and chocolate for a sweet treat.', '6/14/2024', '12/14/2030');
insert into PRODUCTS (id, name, sku, product_type, description, created_at, updated_at) values (4, 'Vegetable Spring Rolls', 'PRD-C8C2FH', 'STRING', 'Crispy spring rolls filled with vegetables', '2/8/2021', '11/21/2015');
insert into PRODUCTS (id, name, sku, product_type, description, created_at, updated_at) values (5, 'Silicone Ice Cube Tray', 'PRD-SMGTXE', 'STRING', 'Flexible tray for easy-release ice cubes.', '8/5/2025', '5/1/2023');

-- WAREHOUSES sample data
insert into WAREHOUSES (id, user_id, name, warehouse_type, capacity, location, created_at, updated_at) values (1, 1, 'Sunset Storage', 'INSTRUMENTS', 12045, 'Dallas, TX', '7/20/2016', '9/9/2015');
insert into WAREHOUSES (id, user_id, name, warehouse_type, capacity, location, created_at, updated_at) values (2, 1, 'Golden Gate Logistics', 'INSTRUMENTS', 37744, 'Los Angeles, CA', '1/14/2019', '3/1/2021');
insert into WAREHOUSES (id, user_id, name, warehouse_type, capacity, location, created_at, updated_at) values (3, 1, 'Sunset Storage', 'INSTRUMENTS', 16656, 'Los Angeles, CA', '6/27/2020', '5/19/2015');
insert into WAREHOUSES (id, user_id, name, warehouse_type, capacity, location, created_at, updated_at) values (4, 1, 'Sunset Storage', 'INSTRUMENTS', 38716, 'Dallas, TX', '5/14/2022', '6/30/2024');
insert into WAREHOUSES (id, user_id, name, warehouse_type, capacity, location, created_at, updated_at) values (5, 1, 'Oceanfront Warehouse', 'INSTRUMENTS', 32867, 'Dallas, TX', '2/16/2020', '2/1/2025');

-- SECTIONS sample data
insert into SECTIONS (id, warehouse_id, name, created_at, updated_at) values (1, 1, 'STRING', '5/24/2018', '9/19/2019');
insert into SECTIONS (id, warehouse_id, name, created_at, updated_at) values (2, 1, 'BRASS', '7/15/2017', '12/6/2019');
insert into SECTIONS (id, warehouse_id, name, created_at, updated_at) values (3, 1, 'PERCUSSION', '8/10/2022', '3/25/2015');
insert into SECTIONS (id, warehouse_id, name, created_at, updated_at) values (4, 2, 'OTHER', '9/16/2016', '4/1/2020');
insert into SECTIONS (id, warehouse_id, name, created_at, updated_at) values (5, 2, 'STRING', '9/1/2023', '2/9/2019');

-- SECTIONS_PRODUCTS sample data
insert into SECTIONS_PRODUCTS (section_id, product_id, quantity, created_at) values (1, 1, 770, '11/18/2017');
insert into SECTIONS_PRODUCTS (section_id, product_id, quantity, created_at) values (2, 2, 10, '5/25/2014');
insert into SECTIONS_PRODUCTS (section_id, product_id, quantity, created_at) values (3, 3, 948, '4/2/2020');
insert into SECTIONS_PRODUCTS (section_id, product_id, quantity, created_at) values (4, 4, 738, '11/20/2011');
insert into SECTIONS_PRODUCTS (section_id, product_id, quantity, created_at) values (5, 5, 882, '3/1/2027');


SELECT setval('warehouses_id_seq', (SELECT COALESCE(MAX(id), 0) FROM warehouses));
