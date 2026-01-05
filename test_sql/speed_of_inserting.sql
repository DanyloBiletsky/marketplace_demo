DROP TABLE IF EXISTS product_bench;
CREATE SEQUENCE IF NOT EXISTS product_bench_seq START 1;
CREATE UNLOGGED TABLE product_bench AS
SELECT
    nextval('product_bench_seq')::bigint AS id,
            'Product ' || gs AS name,
    round((random()*1000)::numeric,2) AS price,
    'Description for Product ' || gs || ': ' || encode(digest((random()::text),'sha1'),'hex') AS description,
    now() - (random() * interval '365 days') AS created_at,
    now() - (random() * interval '30 days') AS updated_at
FROM generate_series(1,100000) gs;


