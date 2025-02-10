-- Inserts para la tabla USER
INSERT INTO USERM (id,uname, email, passw) VALUES
    ('123e4567-e89b-12d3-a456-426614174001','John Doe', 'john.doe@example.com', 'password123'),
    ('123e4567-e89b-12d3-a456-426614174002','Alice Smith', 'alice.smith@example.com', 'securepass'),
    ('123e4567-e89b-12d3-a456-426614174003','Bob Johnson', 'bob.johnson@example.com', 'mysecretpassword');

-- Inserts para la tabla PHONE
INSERT INTO PHONE (pnumber, city_code, country_code, user_id) VALUES
    ('123-456-7890', 123.45, 1, '123e4567-e89b-12d3-a456-426614174001'),
    ('987-654-3210', 987.65, 2, '123e4567-e89b-12d3-a456-426614174002'),
    ('555-123-4567', 555.55, 3, '123e4567-e89b-12d3-a456-426614174003'),
    ('555-987-6543', 555.98, 1, '123e4567-e89b-12d3-a456-426614174001'),
    ('222-333-4444', 222.33, 2, '123e4567-e89b-12d3-a456-426614174002'),
    ('777-888-9999', 777.88, 3, '123e4567-e89b-12d3-a456-426614174003');

INSERT INTO CONFIGURATIONAPP (name_parameter, value_parameter) VALUES
    ('REGEX_PASSWORD', '^(?=.*[A-Z])(?=.*[!@#$%^&*()-+_?])(?=.*\d).{12,}$');