CREATE INDEX orders_created_at_idx ON pnipu_highload.orders (created_at);
CREATE INDEX orders_customer_id_idx ON pnipu_highload.orders (customer_id);
CREATE INDEX orders_2026_02_status_idx ON pnipu_highload.orders_2026_02 (status);
CREATE INDEX orders_2026_03_total_amount_idx ON pnipu_highload.orders_2026_03 (total_amount);
