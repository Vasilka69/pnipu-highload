INSERT INTO pnipu_highload.orders (customer_id, status, total_amount, created_at, description)
VALUES
    (101, 'NEW', 1590.00, '2026-01-05', 'January order 1'),
    (102, 'PAID', 3499.90, '2026-01-12', 'January order 2'),
    (103, 'SHIPPED', 2199.50, '2026-01-28', 'January order 3');

INSERT INTO pnipu_highload.orders (customer_id, status, total_amount, created_at, description)
VALUES
    (101, 'PAID', 4990.00, '2026-02-03', 'February order 1'),
    (104, 'NEW', 799.99, '2026-02-14', 'February order 2'),
    (105, 'CANCELLED', 1299.00, '2026-02-21', 'February order 3');

INSERT INTO pnipu_highload.orders (customer_id, status, total_amount, created_at, description)
VALUES
    (106, 'NEW', 999.00, '2026-03-02', 'March order 1'),
    (102, 'PAID', 18900.00, '2026-03-10', 'March order 2'),
    (107, 'SHIPPED', 4590.00, '2026-03-18', 'March order 3');

INSERT INTO pnipu_highload.orders (customer_id, status, total_amount, created_at, description)
VALUES
    (106, 'NEW', 999.00, '2007-03-02', 'Default order 1'),
    (102, 'PAID', 18900.00, '2007-03-10', 'Default order 2'),
    (107, 'SHIPPED', 4590.00, '2007-03-18', 'Default order 3');

INSERT INTO pnipu_highload.orders (customer_id, status, total_amount, created_at, description)
SELECT
    (100 + (random() * 10)::int) AS customer_id,
    (ARRAY['NEW', 'PAID', 'SHIPPED', 'CANCELLED'])[floor(random() * 4 + 1)] AS status,
    round((random() * 20000)::numeric, 2) AS total_amount,
    date '2026-03-01' + ((random() * 30)::int) AS created_at,
    'Generated order #' || gs AS description
FROM generate_series(1, 1000000) AS gs;
