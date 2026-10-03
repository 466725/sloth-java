USE slothdb;
SHOW DATABASES;

SELECT * 
FROM students
WHERE major IN('Computer Engineering', 'Computer Science');

ALTER TABLE employee
ADD FOREIGN KEY(branch_id)
REFERENCES branch(branch_id)
ON DELETE SET NULL;

ALTER TABLE employee
ADD FOREIGN KEY(sup_id)
REFERENCES employee(emp_id)
ON DELETE SET NULL;

INSERT INTO branch VALUES(3, 'Info', NULL);

INSERT INTO employee VALUES(206, 'Yellow', '1998-02-04', 'F', 50000, 1, NULL);
INSERT INTO employee VALUES(207, 'Green', '1985-07-03', 'M', 29000, 2, 206);
INSERT INTO employee VALUES(208, 'Black', '2000-12-06', 'M', 35000, 3, 206);
INSERT INTO employee VALUES(209, 'White', '1997-10-22', 'F', 39000, 3, 207);
INSERT INTO employee VALUES(210, 'Blue', '1949-08-17', 'F', 84000, 1, 207);

UPDATE branch
SET manager_id = 208
WHERE branch_id = 3;

INSERT INTO client VALUES(400, 'dog', 3653214589);

INSERT INTO work_with VALUE(206, 400, '70000');
INSERT INTO work_with VALUE(207, 401, '24000');
INSERT INTO work_with VALUE(208, 402, '9800');
INSERT INTO work_with VALUE(208, 403, '24000');
INSERT INTO work_with VALUE(210, 404, '87900');

SELECT * 
FROM branches;

SELECT * 
FROM employee
ORDER BY salary DESC
LIMIT 3;

SELECT count(*) 
FROM employee
WHERE birth_date > '1970-01-01'
AND sex = 'F';

SELECT name
FROM employee
	UNION
	SELECT client_name
	FROM client
		UNION
		SELECT branch_name
		FROM branch;

SELECT emp_id, name, branch_name
FROM employee
JOIN branch ON emp_id = manager_id;

SELECT emp_id, name, branch_name
FROM employee
LEFT JOIN branch ON emp_id = manager_id;

SELECT name
FROM employee
WHERE emp_id = (
	SELECT manager_id
    FROM branch
    WHERE branch_name = 'R&D'
);

SELECT name
FROM employee
WHERE emp_id IN (
	SELECT emp_id
    FROM work_with
    WHERE total_sales > 50000
);


