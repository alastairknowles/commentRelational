create table comment (
  id      int(20) unsigned not null auto_increment,
  comment text             not null,
  name    text             not null,
  posted  datetime         not null,
  primary key (id)
)
  collate = utf8_general_ci
  engine = innodb;
