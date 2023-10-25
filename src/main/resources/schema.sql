CREATE TABLE USERS (
	id bigint auto_increment primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	gender ENUM('MALE', 'FEMALE') not null,
	email varchar(255) not null,
	password varchar(255) not null,
	role enum('ADMIN', 'USER') not null,
    logged boolean not null default false
);

CREATE TABLE POSTS (
    id bigint auto_increment primary key,
    content varchar(600),
    picture BLOB,
    created timestamp not null,
    user_id bigint not null references USERS(id)
);

CREATE TABLE COMMENTS (
    id bigint auto_increment primary key,
    content varchar(600) not null,
    created timestamp not null,
    user_id bigint not null references USERS(id),
    post_id bigint not null references POSTS(id)
);

CREATE TABLE USER_FRIENDS (
    id bigint auto_increment primary key,
    user_id bigint not null references USERS(id),
    friend_id bigint not null references USERS(id)
);

CREATE TABLE SESSIONS (
	id bigint auto_increment primary key,
	user_id bigint not null references USERS(id),
	token varchar(255) not null,
	until timestamp not null
);

CREATE TABLE POST_LIKES(
    id bigint auto_increment primary key,
    user_id bigint references USERS(id),
    post_id bigint references POSTS(id)
);

CREATE TABLE POST_DISLIKES(
    id bigint auto_increment primary key,
    user_id bigint references USERS(id),
    post_id bigint references POSTS(id)
);

CREATE TABLE COMMENT_LIKES(
    id bigint auto_increment primary key,
    user_id bigint references USERS(id),
    comment_id bigint references COMMENTS(id)
);

CREATE TABLE COMMENT_DISLIKES(
    id bigint auto_increment primary key,
    user_id bigint references USERS(id),
    comment_id bigint references COMMENTS(id)
);
