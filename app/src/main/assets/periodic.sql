--
-- File generated with SQLiteStudio v3.0.7 on Mon Jul 10 14:17:40 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Elements
DROP TABLE IF EXISTS Elements;

CREATE TABLE Elements (
    Atomic_Number  INTEGER PRIMARY KEY,
    Symbol         TEXT,
    Name           TEXT,
    NameRus        TEXT,
    Atomic_Weight  FLOAT,
    Block          TEXT,
    Family         INTEGER,
    Radioactive    INTEGER,
    Melting_Point  FLOAT,
    Boiling_Point  FLOAT,
    discovery_year INTEGER
);


-- Table: Isotopes
DROP TABLE IF EXISTS Isotopes;

CREATE TABLE Isotopes (
    Atomic_number    INTEGER,
    Isotope_Z        INTEGER,
    Isotope_N        INTEGER,
    Isotope_Mass     DOUBLE,
    Isotope_Name     TEXT,
    Isotope_Name_Rus TEXT
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
