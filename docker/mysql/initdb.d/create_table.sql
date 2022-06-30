create schema mileage collate utf8mb4_unicode_ci;
use mileage;

create table mileage
(
    user_id           binary(16) not null primary key,
    accumulated_point int        not null
);

create table review_mileage
(
    place_id     binary(16) not null,
    review_id    binary(16) not null,
    user_id      binary(16) not null,
    first_review bit        not null,
    has_content  bit        not null,
    has_photo    bit        not null,
    primary key (place_id, review_id, user_id)
);
