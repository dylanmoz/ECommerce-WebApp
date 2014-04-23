CREATE TABLE account (
  id            SERIAL PRIMARY KEY,
  username      TEXT UNIQUE NOT NULL,
  role          TEXT NOT NULL,
  age           INTEGER NOT NULL,
  state         TEXT NOT NULL
);

CREATE TABLE category (
  id            SERIAL PRIMARY KEY,
  name          TEXT UNIQUE NOT NULL,
  description   TEXT
);

CREATE TABLE product (
  id            SERIAL PRIMARY KEY,
  name          TEXT NOT NULL,
  category      INTEGER REFERENCES category (id) NOT NULL,
  sku           TEXT UNIQUE NOT NULL,
  price         FLOAT NOT NULL
);

CREATE TABLE shoppingcart (
  id            SERIAL PRIMARY KEY,
  quantity		INTEGER NOT NULL,
  account       INTEGER REFERENCES account (id) NOT NULL,
  product       INTEGER REFERENCES product (id) NOT NULL
);