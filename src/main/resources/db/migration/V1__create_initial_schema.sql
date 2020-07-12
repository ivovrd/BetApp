CREATE TABLE account (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    username varchar (50) NOT NULL,
    first_name varchar (50),
    last_name varchar (50),
    balance float,
    PRIMARY KEY (id),
    UNIQUE KEY UK_username (username)
);

CREATE TABLE transactions (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    account_id bigint(20),
    trans_type varchar(10),
    CONSTRAINT chk_trans_type CHECK (trans_type IN ('IN', 'OUT')),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE ticket (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    transaction_id bigint(20),
    bet float,
    quota float,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id)
);

CREATE TABLE sporting_event (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    text varchar(100),
    quota1 float,
    quotaX float DEFAULT NULL,
    quota2 float,
    sport varchar(20),
    CONSTRAINT chk_sport CHECK (sport IN('nogomet' ,'kosarka', 'tenis'))
);

CREATE TABLE played_events (
    --id bigint(20) NOT NULL AUTO_INCREMENT,
    ticket_id bigint(20),
    event_id bigint(20),
    type_played char(1),
    CONSTRAINT chk_type CHECK (type_played IN ('1', 'X', '2')),
    FOREIGN KEY (ticket_id) REFERENCES ticket(id),
    FOREIGN KEY (event_id) REFERENCES sporting_event(id),
    PRIMARY KEY (ticket_id, event_id)
);