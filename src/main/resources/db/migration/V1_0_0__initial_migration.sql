CREATE TABLE IF NOT EXISTS product (
    id              UUID,
    name            VARCHAR(32)     DEFAULT NULL,
    description     VARCHAR(32)     DEFAULT NULL,
    price_value     NUMERIC(12,4)   DEFAULT 0.0,

    row_created_at  TIMESTAMP       DEFAULT NULL    NOT NULL,
    row_updated_at  TIMESTAMP       DEFAULT NULL    NOT NULL,
    row_index       INT             DEFAULT 0       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category (
    id              UUID            NOT NULL,
    product_id      UUID            NOT NULL,
    description     VARCHAR(32)     DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
