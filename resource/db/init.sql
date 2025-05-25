--resources/db/init.sql
--创建管理员表
create table if not exists admin(
                                    id Integer primary key autoincrement ,
                                    username text unique not null ,
                                    password text not null
);

--创建教师表
create table if not exists teacher(
                                      teacher_id integer primary key autoincrement ,
                                      name text not null ,
                                      course text not null ,
                                      username text unique not null ,
                                      password text not null
);

--创建学生表
create table if not exists student(
                                      student_id text primary key ,
                                      name text not null ,
                                      password text not null ,
                                      java_course integer,
                                      os_course integer
);

--插入测试数据
insert into admin (username,password) values ('1','123456');

insert into teacher(name,course,username,password)
values ('杨天成','Java','java_teacher','java123'),
       ('张浩天','操作系统','os_teacher','os123');

insert into student(student_id,name,password)
values ('S001','苗长清','123456'),
       ('S002','张子衡','123456'),
       ('S003','孙振兴','123456');