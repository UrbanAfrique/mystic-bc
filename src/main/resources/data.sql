INSERT INTO users (id, username, password, email, role) VALUES
(1, 'admin', '$2a$10$EIX1g5G9Z1Z1Z1Z1Z1Z1Z1', 'admin@example.com', 'ROLE_ADMIN'),
(2, 'user', '$2a$10$EIX1g5G9Z1Z1Z1Z1Z1Z1Z1', 'user@example.com', 'ROLE_SELLER');

INSERT INTO hotels (id, name, description, location, owner_id) VALUES
(1, 'Hotel Casablanca', 'A beautiful hotel in the heart of Casablanca.', 'Casablanca', 1),
(2, 'Riad Marrakech', 'A traditional riad in Marrakech.', 'Marrakech', 1);

INSERT INTO events (id, name, description, date, location) VALUES
(1, 'Marrakech Festival', 'A vibrant festival celebrating Moroccan culture.', '2023-12-01', 'Marrakech'),
(2, 'Casablanca Jazz Nights', 'Enjoy live jazz music in Casablanca.', '2023-11-15', 'Casablanca');

INSERT INTO packages (id, name, description, price, owner_id) VALUES
(1, 'Moroccan Adventure', 'Explore the beauty of Morocco with this package.', 999.99, 1),
(2, 'Cultural Experience', 'Immerse yourself in Moroccan culture.', 499.99, 1);

INSERT INTO bookings (id, user_id, package_id, hotel_id, event_id, status) VALUES
(1, 2, 1, 1, 1, 'CONFIRMED'),
(2, 2, 2, 2, 2, 'PENDING');

INSERT INTO transports (id, type, description, owner_id) VALUES
(1, 'Bus', 'Comfortable bus service for tourists.', 1),
(2, 'Taxi', 'Reliable taxi service in the city.', 1);

INSERT INTO artisan_products (id, name, description, price, owner_id) VALUES
(1, 'Handmade Pottery', 'Beautiful handmade pottery from local artisans.', 25.00, 1),
(2, 'Traditional Carpet', 'Authentic Moroccan carpet.', 150.00, 1);

INSERT INTO food_experiences (id, name, description, price, owner_id) VALUES
(1, 'Cooking Class', 'Learn to cook traditional Moroccan dishes.', 75.00, 1),
(2, 'Food Tour', 'Explore the flavors of Morocco with a guided food tour.', 50.00, 1);

INSERT INTO statistics (id, total_bookings, total_users, total_hotels) VALUES
(1, 100, 50, 10);