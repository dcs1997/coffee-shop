-- Insert Categories
INSERT INTO category (id, name, description) VALUES
(1, 'Espresso', 'Strong and bold coffee shots'),
(2, 'Brewed Coffee', 'Freshly brewed coffee from premium beans'),
(3, 'Cold Coffee', 'Chilled coffee beverages with milk and ice'),
(4, 'Pastries', 'Freshly baked pastries to complement coffee'),
(5, 'Tea', 'A variety of aromatic teas');

-- Insert Products
INSERT INTO product (id, name, description, available_quantity, price, category_id) VALUES
(1, 'Espresso Shot', 'A single strong espresso shot', 100, 2.50, 1),
(2, 'Double Espresso', 'A double shot of espresso for extra kick', 80, 4.00, 1),
(3, 'Americano', 'Espresso diluted with hot water', 90, 3.00, 2),
(4, 'Latte', 'Smooth espresso with steamed milk', 85, 4.50, 2),
(5, 'Iced Latte', 'Chilled latte with ice and milk', 70, 5.00, 3),
(6, 'Cappuccino', 'Espresso with a rich layer of foamed milk', 75, 4.75, 2),
(7, 'Caramel Frappuccino', 'Blended iced coffee with caramel and whipped cream', 50, 5.50, 3),
(8, 'Chocolate Croissant', 'Buttery croissant filled with chocolate', 60, 3.50, 4),
(9, 'Blueberry Muffin', 'Soft and fluffy muffin with blueberries', 65, 3.00, 4),
(10, 'Green Tea', 'Refreshing green tea with antioxidants', 95, 2.75, 5);

-- Ensure the sequences start correctly
SELECT setval('category_seq', (SELECT MAX(id) FROM category) + 50);
SELECT setval('product_seq', (SELECT MAX(id) FROM product) + 50);
