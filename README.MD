# Command line password manager wirtten in Babashka and Clojure
When run first time - your master key will be asked.
Please,


## Tech stack
- Clojure
- Babashka
- SqlLite DB
- Stash (encrypted text storage)

## Usage

1. Run
```console
$ bb -m passman.app
```

2. Create DB and the "redirects" table via pgsql or another tool you prefer

```pgsql> 
CREATE DATABASE shorturl;
```

```pgsql> 
CREATE TABLE redirects(
  slug varchar(10) PRIMARY KEY NOT NULL,
  url varchar(1000) NOT NULL                     
);
```