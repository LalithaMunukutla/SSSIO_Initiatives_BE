-- Creating table SSSIO_ACTIVITY
CREATE TABLE SSSIO_ACTIVITY (
    activity_id SERIAL PRIMARY KEY,
    activity_name VARCHAR(255) NOT NULL,
    activity_description TEXT,
    activity_superType INTEGER,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Creating table SSSIO_ACTIVITY_QUES_TYPES
CREATE TABLE SSSIO_ACTIVITY_QUES_TYPES (
    ques_type_id SERIAL PRIMARY KEY,
    ques_type VARCHAR(255) NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Creating table SSSIO_ACTIVITY_QUES
CREATE TABLE SSSIO_ACTIVITY_QUES (
    ques_id SERIAL PRIMARY KEY,
    activity_id INTEGER NOT NULL,
    ques_description TEXT,
    ques_type_id INTEGER NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (activity_id) REFERENCES SSSIO_ACTIVITY (activity_id),
    FOREIGN KEY (ques_type_id) REFERENCES SSSIO_ACTIVITY_QUES_TYPES (ques_type_id)
);

-- Creating table SSSIO_ACTIVITY_QUES_OPTIONS
CREATE TABLE SSSIO_ACTIVITY_QUES_OPTIONS (
    option_id SERIAL PRIMARY KEY,
    option_description TEXT,
    option_description_2 TEXT,
    ques_id INTEGER NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ques_id) REFERENCES SSSIO_ACTIVITY_QUES (ques_id)
);

-- Creating table SSSIO_ZONES
CREATE TABLE SSSIO_ZONES (
    zone_id SERIAL PRIMARY KEY,
    zone_name VARCHAR(255) NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Creating table SSSIO_COUNTRIES
CREATE TABLE SSSIO_COUNTRIES (
    country_id SERIAL PRIMARY KEY,
    country_name VARCHAR(255) NOT NULL,
    zone_id INTEGER NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (zone_id) REFERENCES SSSIO_ZONES (zone_id)
);

-- Creating table SSSIO_REGION
CREATE TABLE SSSIO_REGION (
    region_id SERIAL PRIMARY KEY,
    region_name VARCHAR(255) NOT NULL,
    country_id INTEGER NOT NULL,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (country_id) REFERENCES SSSIO_COUNTRIES (country_id)
);

-- Creating table SSSIO_ROLE
CREATE TABLE SSSIO_ROLE (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    can_approve BOOLEAN DEFAULT FALSE,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Creating table SSSIO_USER
CREATE TABLE SSSIO_USER (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    country INTEGER,
    zone INTEGER,
    region INTEGER,
    role_id INTEGER,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by VARCHAR(255),
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by VARCHAR(255),
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (country) REFERENCES SSSIO_COUNTRIES (country_id),
    FOREIGN KEY (zone) REFERENCES SSSIO_ZONES (zone_id),
    FOREIGN KEY (region) REFERENCES SSSIO_REGION (region_id),
    FOREIGN KEY (role_id) REFERENCES SSSIO_ROLE (role_id)
);

-- Creating table SSSIO_ACTIVITY_RESPONSES (first instance)
CREATE TABLE SSSIO_ACTIVITY_RESPONSES (
    response_id SERIAL PRIMARY KEY,
    activity_id INTEGER NOT NULL,
    activity_date DATE,
    country_id INTEGER,
    region_id INTEGER,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (activity_id) REFERENCES SSSIO_ACTIVITY (activity_id),
    FOREIGN KEY (country_id) REFERENCES SSSIO_COUNTRIES (country_id),
    FOREIGN KEY (region_id) REFERENCES SSSIO_REGION (region_id)
);

-- Creating second instance of SSSIO_ACTIVITY_RESPONSES 
-- Note: In a real schema, these would likely be different tables with different names
-- For the purpose of this exercise, I'm adding a suffix to distinguish them
CREATE TABLE SSSIO_ACTIVITY_RESPONSES_DETAIL (
    selection_id SERIAL PRIMARY KEY,
    response_id INTEGER NOT NULL,
    ques_id INTEGER NOT NULL,
    response_desc TEXT,
    version INTEGER DEFAULT 1,
    status VARCHAR(50) DEFAULT 'active',
    created_by INTEGER,
    created_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    mod_by INTEGER,
    mod_time TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (response_id) REFERENCES SSSIO_ACTIVITY_RESPONSES (response_id),
    FOREIGN KEY (ques_id) REFERENCES SSSIO_ACTIVITY_QUES (ques_id)
);