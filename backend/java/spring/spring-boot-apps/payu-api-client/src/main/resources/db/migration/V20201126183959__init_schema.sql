CREATE TABLE orders (
    id VARCHAR(36) UNIQUE NOT NULL,
    order_id VARCHAR(255) UNIQUE NOT NULL,
    created_at timestamp not null,

    PRIMARY KEY(id)
);
