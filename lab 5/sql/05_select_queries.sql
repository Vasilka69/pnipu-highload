EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE created_at >= DATE '2026-02-01'
  AND created_at < DATE '2026-03-01';

EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE customer_id = 101
ORDER BY created_at DESC;


EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE
    status = 'PAID' AND
    created_at >= DATE '2026-01-01' AND
    created_at < DATE '2026-03-01';

EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE total_amount > 3000
  AND created_at >= DATE '2026-03-01'
  AND created_at < DATE '2026-04-01';

EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE created_at >= DATE '2026-03-01'
  AND created_at < DATE '2026-04-01';

EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE customer_id = 102
  AND created_at >= DATE '2026-03-01'
  AND created_at < DATE '2026-04-01';

EXPLAIN ANALYZE
SELECT order_id, customer_id, status, total_amount, created_at
FROM pnipu_highload.orders
WHERE total_amount = 18900
  AND created_at >= DATE '2026-03-01'
  AND created_at < DATE '2026-04-01';
