INSERT INTO specialties (NAME)
    VALUES ('Java developer'),
           ('JS developer'),
           ('Web designer'),
           ('SENIOR HTML DEVELOPER');

INSERT INTO SKILLS (NAME)
    VALUES ('Spring framework'),
           ('SQL'),
           ('REACT'),
           ('English B2'),
           ('alcoholism');

INSERT INTO DEVELOPERS (FIRST_NAME, LAST_NAME, SPECIALTY_ID, STATUS)
    VALUES ('Darth', 'Vader', 1, 'ACTIVE'),
           ('Petya', 'Vasechkin', 2, 'ACTIVE'),
           ('Alisa', 'Selezneva', 3, 'ACTIVE'),
           ('Vasiliy', 'Fortochkin', 4, 'DELETED');

INSERT INTO DEVELOPER_SKILLS (developer_id, skill_id)
    VALUES (1, 1),
           (1, 2),
           (1, 4),
           (2, 3),
           (3, 4),
           (4, 5);