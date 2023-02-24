CREATE TABLE nodes (
    code integer primary key not null,
    url varchar(1024) not null,
    last_change date,
    visited_at date
);

CREATE TABLE edges (
    src integer not null references nodes(code),
    dst integer not null references nodes(code)
)