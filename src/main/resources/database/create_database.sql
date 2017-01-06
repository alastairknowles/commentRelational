create schema comment_local;
create schema comment_test;
create user 'comment'@'%'
  identified by 'comment';
grant all on comment_local.* to 'comment'@'%';
grant all on comment_test.* to 'comment'@'%';
