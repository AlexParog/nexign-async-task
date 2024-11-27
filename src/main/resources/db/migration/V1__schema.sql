CREATE TABLE task
(
    id               BIGSERIAL PRIMARY KEY,
    task_name        VARCHAR(255) NOT NULL,
    status           VARCHAR(50)  NOT NULL DEFAULT 'CREATED',
    result           TEXT,
    duration         BIGINT       NOT NULL,
    created_at       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
)