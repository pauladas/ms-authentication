CREATE TABLE IF NOT EXISTS user
(
    id varchar(36) PRIMARY KEY,
    name varchar(100),
    email varchar(100),
    password varchar(200),
    created_date datetime,
    updated_date datetime
);