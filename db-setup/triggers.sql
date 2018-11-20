-- trig_c_enroll
create or replace trigger trig_enroll_student
before insert on enrollments
for each row
declare
seats number;
begin

select (c.limit - c.class_size) into seats
from classes c where c.classid = :new.classid;

if (seats < 1) then
raise_application_error(-20010, 'No seats left for this class');
else
dbms_output.put_line('Updated class_size');
update classes set class_size = class_size + 1
where classid = :new.classid;
end if;
end;
/


-- trig_d_enroll
create or replace trigger trig_drop_enroll
after delete on enrollments
for each row
begin
update classes set class_size = class_size - 1
where classid = :old.classid;
end;
/

-- trig_d_student
create or replace trigger trig_drop_student
after delete on students
for each row
begin
-- dbms_output.put_line('Removed tas from classes');
update classes set ta_B# = null
where ta_B# = :old.b#;

-- dbms_output.put_line('Removed tas from tas');
delete from tas where b# = :old.b#;

-- dbms_output.put_line('Removing all enrollments');
-- Will trigger enrollments delete triggers
delete from enrollments where b# = :old.b#;
end;
/

-- trig_log
drop sequence log_sequence;
create sequence log_sequence start with 100 increment by 1 order;

create or replace trigger trig_log_number
before insert on logs
for each row
begin
  select log_sequence.nextval into :new.log# from dual;
end;
/

create or replace trigger trig_log_d_student
after delete on students
for each row
declare
  name varchar(50);
begin
  select user into name from dual;
  insert into logs(op_name, op_time, table_name, operation, key_value)
  values(name, sysdate, 'students', 'delete', :old.b#);
end;
/

create or replace trigger trig_log_i_enrollment
after insert on enrollments
for each row
declare
  name varchar(50);
begin
  select user into name from dual;
  insert into logs(op_name, op_time, table_name, operation, key_value)
  values(name, sysdate, 'enrollments', 'insert', :new.b# || ',' || :new.classid);
end;
/

create or replace trigger trig_log_d_enrollment
after delete on enrollments
for each row
declare
  name varchar(50);
begin
  select user into name from dual;
  insert into logs(op_name, op_time, table_name, operation, key_value)
  values(name, sysdate, 'enrollments', 'delete', :old.b# || ',' || :old.classid);
end;
/

-- trig_d_ta
create or replace trigger trig_drop_ta
after delete on tas
for each row
begin
-- dbms_output.put_line('Removed tas from classes');
update classes set ta_B# = null
where ta_B# = :old.b#;
end;
/

-- trig_d_class
create or replace trigger trig_drop_class
before delete on classes
for each row
begin
  delete from enrollments where classid = :old.classid;
end;
/
