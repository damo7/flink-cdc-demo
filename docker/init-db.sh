#!/bin/bash
psql -U postgres -d cdc_db <<EOF
CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    shop_id INT,
    product_name VARCHAR(255),
    quantity INT,
    price DECIMAL(10, 2),
    sale_time TIMESTAMP
);

INSERT INTO sales (shop_id, product_name, quantity, price, sale_time)
VALUES 
(2, 'Tropical Smoothie', 5, 4.99, NOW());
EOF
