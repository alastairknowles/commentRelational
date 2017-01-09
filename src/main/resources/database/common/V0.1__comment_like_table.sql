create table comment_like (
  id         int(20) unsigned not null auto_increment,
  comment_id int(20) unsigned not null,
  primary key (id),
  index (comment_id),
  foreign key (comment_id) references comment (id)
)
  collate = utf8_general_ci
  engine = innodb;
