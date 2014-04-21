CREATE TABLE account (
  id            SERIAL PRIMARY KEY,
  username      TEXT UNIQUE,
  role          TEXT,
  age           INTEGER,
  state         TEXT
);

CREATE TABLE category (
  id            SERIAL PRIMARY KEY,
  name          TEXT UNIQUE,
  description   TEXT
);

CREATE TABLE product (
  id            SERIAL PRIMARY KEY,
  name          TEXT,
  category      INTEGER REFERENCES category (id) NOT NULL,
  sku           TEXT UNIQUE,
  price         FLOAT
);

CREATE TABLE shoppingcart (
  id            SERIAL PRIMARY KEY,
  account       INTEGER REFERENCES account (id) NOT NULL,
  product       INTEGER REFERENCES product (id) NOT NULL
);