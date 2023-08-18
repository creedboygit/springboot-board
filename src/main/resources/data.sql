-- 테스트 계정
-- TODO: 테스트용이지만 비밀번호가 노출된 데이터 세팅. 개선하는 것이 좋을 지 고민해 보자.
-- insert into user_account (user_id, user_password, nickname, email, memo, created_at, created_by, modified_at, modified_by)
-- values ('uno', '{noop}asdf1234', 'Uno', 'uno@mail.com', 'I am Uno.', now(), 'uno', now(), 'uno')
-- ;
-- insert into user_account (user_id, user_password, nickname, email, memo, created_at, created_by, modified_at, modified_by)
-- values ('uno2', '{noop}asdf1234', 'Uno2', 'uno2@mail.com', 'I am Uno2.', now(), 'uno2', now(), 'uno2')
-- ;
-- insert into user_account (user_id, user_password, nickname, email, memo, created_at, created_by, modified_at, modified_by)
-- values ('uno3', '{noop}asdf1234', 'Uno3', 'uno3@mail.com', 'I am Uno3.', now(), 'uno3', now(), 'uno3')
-- ;

insert into user_account (user_id, user_password, nickname, email, memo, created_at, created_by, modified_at, modified_by)
values ('creedboys', '123123', 'creedboynick', 'creed@creed.com', 'i am creed', now(), 'creed', now(), 'creed');

-- 123 게시글
insert into article (user_id, title, content, created_by, modified_by, created_at, modified_at, hashtag)
values ('creedboys', '제목입니다.11', '내용입니다.11', 'Kamilah', 'Murial', '2021-05-30 23:53:46', '2021-03-10 08:48:50', '해시태그11'),
       ('creedboys', '제목입니다.22', '내용입니다.222', 'Arv', 'Keelby', '2021-05-06 11:51:24', '2021-05-23 08:34:54', '해시태그22');

insert into article_comment (article_id, content, created_by, modified_by, created_at, modified_at)
values (1, '제목입니다.11', 'Kamilah', 'Murial', '2021-05-30 23:53:46', '2021-03-10 08:48:50'),
       (1, '제목입니다.22', 'Arv', 'Keelby', '2021-05-06 11:51:24', '2021-05-23 08:34:54');