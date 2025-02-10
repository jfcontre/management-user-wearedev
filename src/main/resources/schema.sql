CREATE TABLE USERM (
    id uuid DEFAULT random_uuid() PRIMARY KEY,
    uname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passw VARCHAR(500) NOT NULL,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp null default current_timestamp,
    last_login timestamp null default current_timestamp,
    is_active boolean not null default true
);

CREATE TABLE PHONE (
    id uuid DEFAULT random_uuid() PRIMARY KEY,
    pnumber VARCHAR(255),
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    user_id UUID NULL,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp null default current_timestamp,    
    CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES USERM(id)
);

CREATE TABLE CONFIGURATIONAPP (
    id uuid DEFAULT random_uuid() PRIMARY KEY,
    name_parameter VARCHAR(255),
    value_parameter VARCHAR(255)    
);