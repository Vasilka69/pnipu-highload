CREATE TABLE pnipu_highload.orders_2026_04
    PARTITION OF pnipu_highload.orders
    FOR VALUES FROM ('2026-04-01') TO ('2026-05-01');

COMMENT ON TABLE pnipu_highload.orders_2026_04 IS 'Новая партиция заказов за апрель 2026';

DROP TABLE pnipu_highload.orders_2026_01;
