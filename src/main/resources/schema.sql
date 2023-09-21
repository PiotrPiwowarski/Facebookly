CREATE TABLE USERS (
	id bigint auto_increment primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	gender ENUM('Male', 'Female') not null,
	email varchar(255) not null,
	login varchar(255) not null,
	password varchar(255) not null
);

CREATE TABLE POSTS (
    id bigint auto_increment primary key,
    content varchar(600) not null,
    created timestamp not null,
    user_id bigint not null,
    likes bigint default 0,
    dislikes bigint default 0
);

CREATE TABLE COMMENTS (
    id bigint auto_increment primary key,
    content varchar(400) not null,
    created timestamp not null,
    user_id bigint not null,
    post_id bigint not null,
    likes bigint default 0,
    dislikes bigint default 0
);

CREATE TABLE USER_FRIENDS (
    user_id bigint not null,
    friend_id bigint not null
);

ALTER TABLE POSTS
    ADD CONSTRAINT posts_user_id_foreign_key
    FOREIGN KEY (user_id)
	REFERENCES USERS (id);

ALTER TABLE COMMENTS
	ADD CONSTRAINT comments_user_id_foreign_key
	FOREIGN KEY (user_id)
	REFERENCES USERS(id);

ALTER TABLE COMMENTS
	ADD CONSTRAINT comments_comment_id_foreign_key
	FOREIGN KEY (post_id)
	REFERENCES POSTS(id);

ALTER TABLE USER_FRIENDS
	ADD CONSTRAINT user_friends_user_id_foreign_key
    FOREIGN KEY (user_id)
	REFERENCES USERS(id);

ALTER TABLE USER_FRIENDS
	ADD CONSTRAINT user_friends_friend_id_foreign_key
	FOREIGN KEY (friend_id)
	REFERENCES USERS(id);
