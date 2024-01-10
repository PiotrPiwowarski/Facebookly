CREATE TABLE users (
	id bigint auto_increment primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	gender ENUM('MALE', 'FEMALE') not null,
	email varchar(255) not null,
	password varchar(255) not null,
	logged boolean default false,
	role enum('ADMIN', 'USER') not null
);

CREATE TABLE posts (
    id bigint auto_increment primary key,
    content varchar(600),
    image BLOB,
    created timestamp not null,
    user_id bigint not null references users(id) on delete cascade
);

CREATE TABLE comments (
    id bigint auto_increment primary key,
    content varchar(600) not null,
    created timestamp not null,
    user_id bigint not null references users(id) on delete cascade,
    post_id bigint not null references posts(id) on delete cascade
);

CREATE TABLE followed_users (
    id bigint auto_increment primary key,
    user_id bigint not null references users(id) on delete cascade,
    followed_user_id bigint not null references users(id) on delete cascade
);

CREATE TABLE post_reactions(
    id bigint auto_increment primary key,
    user_id bigint references users(id) on delete cascade,
    post_id bigint references posts(id) on delete cascade,
    reaction enum('LIKE', 'DISLIKE') not null
);

CREATE TABLE comment_reactions(
    id bigint auto_increment primary key,
    user_id bigint references users(id) on delete cascade,
    comment_id bigint references comments(id) on delete cascade,
    reaction enum('LIKE', 'DISLIKE') not null
);
