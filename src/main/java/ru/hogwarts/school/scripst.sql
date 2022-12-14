SELECT * FROM "student";

SELECT * FROM "student"
WHERE AGE > 13 AND age<20;

SELECT NAME FROM "student";

SELECT * FROM "student"
WHERE NAME LIKE '%y%';

SELECT * FROM "student"
where AGE < student.id;

SELECT * FROM "student"
ORDER BY AGE, NAME;

SELECT * FROM "faculty";

SELECT s.name,f.name  FROM student as s, faculty as f
WHERE s.faculty_id = f.id
AND s.name = 'HarryPotter';

SELECT s.name  FROM student as s, faculty as f
WHERE s.faculty_id = f.id
AND f.name = 'Griffindor';

SELECT COUNT(*) FROM student;

SELECT AVG(age) FROM student;

SELECT * FROM student ORDER BY id DESC LIMIT 5;