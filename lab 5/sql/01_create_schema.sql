CREATE SCHEMA pnipu_highload;

CREATE TABLE pnipu_highload.orders (
    order_id BIGINT GENERATED ALWAYS AS IDENTITY,
    customer_id BIGINT NOT NULL,
    status VARCHAR(32) NOT NULL,
    total_amount NUMERIC(12, 2) NOT NULL,
    created_at DATE NOT NULL,
    description TEXT,
    PRIMARY KEY (order_id, created_at)
) PARTITION BY RANGE (created_at);

COMMENT ON TABLE pnipu_highload.orders IS 'Родительская таблица заказов, разбитая по диапазонам даты создания';

CREATE TABLE pnipu_highload.orders_2026_01
    PARTITION OF pnipu_highload.orders
    FOR VALUES FROM ('2026-01-01') TO ('2026-02-01');

CREATE TABLE pnipu_highload.orders_2026_02
    PARTITION OF pnipu_highload.orders
    FOR VALUES FROM ('2026-02-01') TO ('2026-03-01');

CREATE TABLE pnipu_highload.orders_2026_03
    PARTITION OF pnipu_highload.orders
    FOR VALUES FROM ('2026-03-01') TO ('2026-04-01');

CREATE TABLE pnipu_highload.orders_default
    PARTITION OF pnipu_highload.orders DEFAULT;

COMMENT ON TABLE pnipu_highload.orders_2026_01 IS 'Партиция заказов за январь 2026';
COMMENT ON TABLE pnipu_highload.orders_2026_02 IS 'Партиция заказов за февраль 2026';
COMMENT ON TABLE pnipu_highload.orders_2026_03 IS 'Партиция заказов за март 2026';
COMMENT ON TABLE pnipu_highload.orders_default IS 'Партиция заказов по умолчанию';
