CREATE TABLE Users(
id number PRIMARY KEY,
login varchar2(50) NOT NULL,
password varchar2(50) NOT NULL,
first_name varchar2(50) NOT NULL,
middle_name varchar2(50) NOT NULL,
last_name varchar2(50) NOT NULL,
user_status varchar2(20) NOT NULL, 
CONSTRAINT check_users CHECK (user_status in ('student','tutor')),
e_mail varchar2(50) NOT NULL
);

CREATE TABLE Students(
id number PRIMARY KEY,
id_student number NOT NULL,
id_test number NOT NULL,
name_test varchar2(200) NOT NULL,
subject varchar2(200) NOT NULL,
name_tutor varchar2(50) NOT NULL,
result varchar2(20) NOT NULL,
pass_test_date date NOT NULL
);

CREATE TABLE TestTutors(
id number PRIMARY KEY,
id_tutor number NOT NULL,
id_test number NOT NULL
);

CREATE TABLE Test(
id number PRIMARY KEY,
name_test varchar2(200) NOT NULL,
subject varchar2(200) NOT NULL,
create_test_date date NOT NULL,
separator varchar2(20) NOT NULL
);

CREATE TABLE TestQuestion(
id number PRIMARY KEY,
id_test number NOT NULL,
id_question number NOT NULL
);

CREATE TABLE Questions(
id number PRIMARY KEY,
question varchar2(1500) NOT NULL
);

CREATE TABLE Answers(
id number PRIMARY KEY,
id_question number NOT NULL,
answer varchar2(1500) NOT NULL,
correct number NOT NULL, 
CONSTRAINT correct CHECK (correct in (0, 1))
);

ALTER TABLE Students ADD CONSTRAINT fk_id_student_u FOREIGN KEY (id_student) REFERENCES Users (id) ON DELETE CASCADE;
ALTER TABLE Students ADD CONSTRAINT fk_id_student_t FOREIGN KEY (id_test) REFERENCES Test (id);
ALTER TABLE TestTutors ADD CONSTRAINT fk_id_test_tutors_u FOREIGN KEY (id_tutor) REFERENCES Users (id) ON DELETE CASCADE;
ALTER TABLE TestTutors ADD CONSTRAINT fk_id_test_tutors_t FOREIGN KEY (id_test) REFERENCES Test (id) ON DELETE CASCADE;
ALTER TABLE TestQuestion ADD CONSTRAINT fk_id_test_question_t FOREIGN KEY (id_test) REFERENCES Test (id) ON DELETE CASCADE;
ALTER TABLE TestQuestion ADD CONSTRAINT fk_id_test_question_q FOREIGN KEY (id_question) REFERENCES Questions (id) ON DELETE CASCADE;
ALTER TABLE Answers ADD CONSTRAINT fk_id_answers FOREIGN KEY (id_question) REFERENCES Questions (id) ON DELETE CASCADE;

CREATE SEQUENCE users_seq start with 1 increment by 1;
CREATE SEQUENCE students_seq start with 1 increment by 1;
CREATE SEQUENCE test_tutors_seq start with 1 increment by 1;
CREATE SEQUENCE test_seq start with 1 increment by 1;
CREATE SEQUENCE test_question_seq start with 1 increment by 1;
CREATE SEQUENCE questions_seq start with 1 increment by 1;
CREATE SEQUENCE answers_seq start with 1 increment by 1;

CREATE OR REPLACE TRIGGER users_trigger_seq
BEFORE INSERT ON Users
FOR EACH ROW
BEGIN
SELECT users_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER students_trigger_seq
BEFORE INSERT ON Students
FOR EACH ROW
BEGIN
SELECT students_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER test_tutors_trigger_seq
BEFORE INSERT ON TestTutors
FOR EACH ROW
BEGIN
SELECT test_tutors_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER test_trigger_seq
BEFORE INSERT ON Test
FOR EACH ROW
BEGIN
SELECT test_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER test_question_seq
BEFORE INSERT ON TestQuestion
FOR EACH ROW
BEGIN
SELECT test_question_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER questions_seq
BEFORE INSERT ON Questions
FOR EACH ROW
BEGIN
SELECT questions_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER answers_seq
BEFORE INSERT ON Answers
FOR EACH ROW
BEGIN
SELECT answers_seq.NEXTVAL
INTO :new.id FROM DUAL;
END;
/

INSERT INTO Users(login, password, first_name, middle_name, last_name, user_status, e_mail) 
VALUES ('katya', '111111', 'Екатерина', 'Сергеевна', 'Беляева', 'student', 'kompp@yandex.ru'); 
INSERT INTO Users(login, password, first_name, middle_name, last_name, user_status, e_mail) 
VALUES ('юля', '222222', 'Юлия', 'Олеговна', 'Сорокина', 'student', 'yulya@mail.ru'); 
INSERT INTO Users(login, password, first_name, middle_name, last_name, user_status, e_mail)
VALUES ('маша', '777777', 'Мария', 'Владимировна', 'Шайгулина', 'tutor', 'mashashaigulina@mail.ru'); 

--DROP TABLE Users CASCADE CONSTRAINTS PURGE;
--DROP TABLE Students CASCADE CONSTRAINTS PURGE;
--DROP TABLE TestTutors CASCADE CONSTRAINTS PURGE;
--DROP TABLE Test CASCADE CONSTRAINTS PURGE;
--DROP TABLE TestQuestion CASCADE CONSTRAINTS PURGE;
--DROP TABLE Questions CASCADE CONSTRAINTS PURGE;
--DROP TABLE Answers CASCADE CONSTRAINTS PURGE;
--DROP TRIGGER users_trigger_seq;
--DROP TRIGGER students_trigger_seq;
--DROP TRIGGER test_tutors_trigger_seq;
--DROP TRIGGER test_trigger_seq;
--DROP TRIGGER test_question_trigger_seq;
--DROP TRIGGER questions_trigger_seq;
--DROP TRIGGER answers_trigger_seq;
--DROP SEQUENCE users_seq;
--DROP SEQUENCE students_seq;
--DROP SEQUENCE test_tutors_seq;
--DROP SEQUENCE test_seq;
--DROP SEQUENCE test_question_seq;
--DROP SEQUENCE questions_seq;
--DROP SEQUENCE answers_seq;