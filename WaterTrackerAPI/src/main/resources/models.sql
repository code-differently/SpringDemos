create database `water-api`

use `water-api`;

create table user_profile(
     `id` varchar(50) primary key,
     `first_name` varchar(50),
     `last_name` varchar(50),
     `email` varchar(100),
     `created` timestamp,
     `updated` timestamp
);

create table `followers`(
    `user_id` varchar(50),
    `follower_id` varchar(50),
    CONSTRAINT pk_followers PRIMARY KEY (`user_id`, `follower_id`)
);