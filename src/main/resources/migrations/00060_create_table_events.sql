CREATE TABLE events (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    description TEXT,
    tasks TEXT,
    event_date INTEGER NOT NULL,
    cancellation_date INTEGER,
    price INTEGER NOT NULL,

    client_id INTEGER NOT NULL,
    promoter_id INTEGER NOT NULL,
    event_type_id INTEGER NOT NULL,
    address_id INTEGER NOT NULL,

    FOREIGN KEY(client_id) REFERENCES clients(id),
    FOREIGN KEY(promoter_id) REFERENCES promoters(id),
    FOREIGN KEY(event_type_id) REFERENCES event_types(id),
    FOREIGN KEY(address_id) REFERENCES addresses(id)
);
