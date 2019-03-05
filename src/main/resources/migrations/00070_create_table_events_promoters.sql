CREATE TABLE events_promoters (
    id INTEGER PRIMARY KEY AUTOINCREMENT,

    event_id INTEGER NOT NULL,
    promoter_id INTEGER NOT NULL,

    FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY(promoter_id) REFERENCES promoters(id)
);
