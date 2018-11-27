create or replace package proc as
  procedure show_students;
  function getAllStudents return sys_refcursor;
  procedure show_enrollments;
  function getAllEnrollments return sys_refcursor;
  procedure show_courses;
  function getAllCourses return sys_refcursor;
  procedure show_tas;
  function getAllTas return sys_refcursor;
  procedure show_classes;
  function getAllClasses return sys_refcursor;
  procedure show_prerequisites;
  function getPrerequisites return sys_refcursor;
  procedure show_class_ta(cid in classes.classid%type);
  function getClassTa(cid in classes.classid%type) return sys_refcursor;
  function getAllPrerequisites(dc in courses.dept_code%type, co in courses.course#%type) return sys_refcursor;
  procedure enroll_student(bnum in students.b#%type, cid in classes.classid%type, courses out number);
  procedure drop_course(bnum in students.b#%type, cid in classes.classid%type, newSize out classes.class_size%type, newCourses out number);
  procedure drop_student(bnum in students.b#%type);
end;
/

create or replace package body proc as
procedure show_students
is
  cursor students_cursor is select * from students;
begin
  for s in students_cursor loop
    dbms_output.put_line(s.b# || ' ' || s.first_name || ' ' || 
                         s.last_name || ' ' || s.status || ' ' || 
                         s.gpa || ' ' || s.email || ' ' || 
                         s.bdate || ' ' || s.deptname
                        );
  end loop;
end;

function getAllStudents
return sys_refcursor
is
  s sys_refcursor;
begin
  open s for select * from students;
  return s;
end;

procedure show_prerequisites
is
  cursor p is select * from prerequisites;
begin
  for pre in p loop
    dbms_output.put_line(pre.dept_code || ' ' || pre.course# || ' ' || pre.pre_dept_code
                         || ' ' || pre.pre_course#);
  end loop;
end;

function getPrerequisites
return sys_refcursor
is
  p sys_refcursor;
begin
  open p for select * from prerequisites;
  return p;
end;

procedure show_enrollments
is
  cursor enroll_cursor is select * from enrollments;
begin
  for e in enroll_cursor loop
    dbms_output.put_line(e.b# || ' ' || e.classid || ' ' || e.lgrade);
  end loop;
end;

function getAllEnrollments
return sys_refcursor
is
  e sys_refcursor;
begin
  open e for select * from enrollments;
  return e;
end;

procedure show_courses
is
  cursor courses_cursor is select * from courses;
begin
  for c in courses_cursor loop
    dbms_output.put_line(c.dept_code || ' ' || c.course# || ' ' || c.title);
  end loop;
end;

function getAllCourses
return sys_refcursor
is
  c sys_refcursor;
begin
  open c for select * from courses;
  return c;
end;

procedure show_tas
is
  cursor tas_cursor is select * from tas;
begin
  for t in tas_cursor loop
    dbms_output.put_line(t.b# || ' ' || t.ta_level || ' ' || t.office);
  end loop;
end;

function getAllTas
return sys_refcursor
is
  t sys_refcursor;
begin
  open t for select * from tas;
  return t;
end;

procedure show_classes
is
  cursor cl_cursor is select * from classes;
begin
  for c in cl_cursor loop
    dbms_output.put_line(c.classid || ' ' || c.dept_code || ' ' || 
                         c.course# || ' ' || c.sect# || ' ' || c.year || ' ' ||
                  	 c.semester || ' ' || c.limit || ' ' || 
                  	 c.class_size || ' ' || c.room || ' ' || nvl(c.ta_B#, 'null')
                        );
  end loop;
end;

function getAllClasses
return sys_refcursor
is
  c sys_refcursor;
begin
  open c for select * from classes;
  return c;
end;

procedure show_class_ta(cid in classes.classid%type) is
  sb# students.b#%type;
  fname students.first_name%type;
  lname students.last_name%type;
begin
  -- Automatically exception raised if classid is invalid
  -- rows will always be 1 when successful
  select ta_B# into sb# from classes c where c.classid = cid;
  -- if no errors then bring in values after check
  if (sb# is not null) then
    select first_name, last_name
    into fname, lname from students
    where b# = sb#;
    dbms_output.put_line(sb# || ' ' || fname || ' ' || lname);
  else
    raise_application_error(-20010, 'The class has no TA');
  end if;
exception
  when no_data_found then 
    raise_application_error(-20010, 'The classid is invalid');
end;

-- Similar functional implementation to use in java code
function getClassTa(cid in classes.classid%type)
return sys_refcursor
is
  sb# students.b#%type;
  scur sys_refcursor;
begin
  select ta_B# into sb# from classes c where c.classid = cid;
  if(sb# is not null) then
    open scur for select * from students where b# = sb#;
  else
    raise_application_error(-20010, 'The class has no TA');
  end if;
  return scur;
exception
  when no_data_found then
    raise_application_error(-20010, 'The classid is invalid');
end;

-- function to return cursor of all indirect and direct prerequisites
function getAllPrerequisites(dc in courses.dept_code%type, co in courses.course#%type)
return sys_refcursor
is
  cur sys_refcursor;
begin
  -- hierarchical query to recursively find prerequisites of prerequisites
  open cur for
  select * from courses where dept_code in (
  select pre_dept_code from prerequisites start with dept_code = dc and course# = co
  connect by prior pre_dept_code = dept_code and prior pre_course# = course#)
  and course# in (
  select pre_course# from prerequisites start with dept_code = dc and course# = co
  connect by prior pre_dept_code = dept_code and prior pre_course# = course#);
  return cur;
end;

procedure enroll_student(bnum in students.b#%type, cid in classes.classid%type, courses out number)
is
  -- variables
  status boolean;
  pre_pass boolean;
  row number;
  pres number;
  cur_pres number;
  dept classes.dept_code%type;
  cnum classes.course#%type;
  seats_left number;
  sem classes.semester%type;
  yr classes.year%type;
  -- exceptions
  no_student exception;
  no_class exception;
  invalid_sem exception;
  no_seats exception;
  enrolled exception;
  overload exception;
  unsatisfied_pre exception;
begin
  -- Check if student exists
  select count(*) into row from students where b# = bnum;
  if (row = 0) then
    raise no_student;
  end if;
  -- Check if class exists
  select count(*) into row from classes where classid = cid;
  if (row = 0) then
    raise no_class;
  end if;
  -- Check if class has room for more
  select (limit - class_size), semester, year into seats_left, sem, yr from classes where classid = cid;
  if (sem != 'Fall' or yr != 2018) then
    raise invalid_sem;
  end if;
  if (seats_left < 1) then
    raise no_seats;
  end if;
  select count(*) into row from enrollments where b# = bnum and classid = cid;
  if (row > 0) then
    raise enrolled;
  end if;
  -- Check how many courses student has current semester
  status := true;
  begin
    -- Count enrollments of all semesters
    -- select count(*) into row from enrollments where b# = bnum group by bnum;
    -- Count enrollments of current semester
    select count(*) into row from enrollments e, classes c
    where e.b# = bnum and e.classid = c.classid 
    and c.semester = 'Fall' and c.year = 2018
    group by (c.semester, c.year);
    courses := row;
  exception
    when no_data_found then
      status := false;
  end;
  if (status) then
    if (row = 4) then
      courses := row;
      dbms_output.put_line('The student will be overloaded with new enrollment');
    elsif (row >= 5) then
      raise overload;
    end if;
  end if;
  pre_pass := true;
  select dept_code, course# into dept, cnum from classes where classid = cid;
  -- Check prerequisites for C minimum
  select count(*) into pres from prerequisites 
  where dept_code = dept and course# = cnum;
  -- dbms_output.put_line('Number of pres => ' || pres);
  cur_pres := 0;
  declare
    cursor cur is
    select lgrade, c.dept_code, c.course# from prerequisites p, enrollments e,
    classes c where p.course# = cnum and p.dept_code = dept and
    c.dept_code = p.pre_dept_code and c.course# = p.pre_course# and
    e.b# = bnum and e.classid = c.classid;
  begin
    for prerec in cur loop
      cur_pres := cur_pres + 1;
      if (prerec.lgrade >= 'C-') then
        pre_pass := false;
      end if;
    end loop;
  end;
  -- dbms_output.put_line('Number of student pres => ' || cur_pres);
  if (cur_pres != pres) then
    raise unsatisfied_pre;
  end if;
  -- If all courses are not graded a minimum of C
  if (not pre_pass) then
    raise unsatisfied_pre;
  end if;
  -- dbms_output.put_line('All conditions met');
  -- All conditions met, insert into enrollments now
  insert into enrollments(b#, classid, lgrade) values(bnum, cid, null);
exception
  when no_student then
    raise_application_error(-20010, 'The B# is invalid');
  when no_class then
    raise_application_error(-20010, 'The classid is invalid');
  when invalid_sem then
    raise_application_error(-20010, 'Cannot enroll into a class from a previous semester');
  when no_seats then
    raise_application_error(-20010, 'The class is already full');
  when enrolled then
    raise_application_error(-20010, 'The student is already in the class');
  when overload then
    raise_application_error(-20010, 'Students cannot be enrolled in more than five classes in the same semester.');
  when unsatisfied_pre then
    raise_application_error(-20010, 'Prerequisite not satisfied');
end;

-- Function used to check if any prerequisites are violated if the course is dropped
function check_pre_v(bnum in students.b#%type, cid in classes.classid%type)
return boolean is
  status boolean;
  dept classes.dept_code%type;
  cnum classes.course#%type;
begin
  select dept_code, course# into dept, cnum from classes where classid = cid;
  status := true;
  declare
    -- Go through all direct and indirect prerequisites
    cursor c is select pre_dept_code, pre_course# from prerequisites where dept_code = dept and course# = cnum
    connect by prior pre_dept_code = dept_code and prior pre_course# = course#;
  begin
    for rec in c loop
      if (dept = rec.pre_dept_code and cnum = rec.pre_course#) then
        status := false;
      end if;
    end loop;
  end;
  return status;
end check_pre_v;

procedure drop_course(bnum students.b#%type, cid classes.classid%type, newSize out classes.class_size%type, newCourses out number)
is
  -- Variables
  status boolean;
  row number;
  -- Exceptions
  no_student exception;
  no_class exception;
  no_enroll exception;
  invalid_sem exception;
  pre_violated exception;
begin
  -- check if student exists
  select count(*) into row from students where b# = bnum;
  if (row = 0) then
    raise no_student;
  end if;
  -- check if class exists
  select count(*) into row from classes where classid = cid;
  if (row = 0) then
    raise no_class;
  end if;
  -- check if enrollment exists
  select count(*) into row from enrollments where b# = bnum and classid = cid;
  if (row = 0) then
    raise no_enroll;
  end if;
  -- check prereq violation
  status := true;
  status := check_pre_v(bnum, cid);
  if (not status) then
    raise pre_violated;
  end if;
  -- when status false, all conditions met, can remove enrollment
  delete from enrollments where b# = bnum and classid = cid;
  select class_size into newSize from classes where classid = cid;
  if (newSize = 0) then
    dbms_output.put_line('The class now has no students');
  end if;
  select count(*) into newCourses from enrollments
  where b# = bnum;
  if (newCourses = 0) then
    dbms_output.put_line('The student is not enrolled in any classes');
  end if;
exception
  when no_student then
    raise_application_error(-20010, 'The B# is invalid');
  when no_class then
    raise_application_error(-20010, 'The classid is invalid');
  when no_enroll then
    raise_application_error(-20010, 'The student is not enrolled in the class.');
  when invalid_sem then
    raise_application_error(-20010, 'Only enrollment in the current semester can be dropped.');
  when pre_violated then
    raise_application_error(-20010, 'Prerequisite requirements violated');
end;

-- All consistency handled by triggers
procedure drop_student(bnum in students.b#%type)
is
  row number;
  no_student exception;
begin
  -- check if student exists then delete
  select count(*) into row from students where b# = bnum;
  if (row = 0) then
    raise no_student;
  end if;
  -- can delete now
  delete from students where b# = bnum;
exception
  when no_student then
    raise_application_error(-20010, 'The B# is invalid.');
end;

end;
/
