CREATE TABLE promoters (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number TEXT,
    bank_account TEXT
);