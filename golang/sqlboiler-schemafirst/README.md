https://github.com/volatiletech/sqlboiler

###test data
    CREATE TABLE pilots (
      id integer NOT NULL,
      name text NOT NULL
    );
    
    ALTER TABLE pilots ADD CONSTRAINT pilot_pkey PRIMARY KEY (id);
    
    CREATE TABLE jets (
      id integer NOT NULL,
      pilot_id integer NOT NULL,
      age integer NOT NULL,
      name text NOT NULL,
      color text NOT NULL
    );
    
    ALTER TABLE jets ADD CONSTRAINT jet_pkey PRIMARY KEY (id);
    -- The following fkey remains poorly named to avoid regressions related to psql naming
    ALTER TABLE jets ADD CONSTRAINT pilots_fkey FOREIGN KEY (pilot_id) REFERENCES pilots(id);
    
    CREATE TABLE languages (
      id integer NOT NULL,
      language text NOT NULL
    );
    
    ALTER TABLE languages ADD CONSTRAINT language_pkey PRIMARY KEY (id);
    
    -- Join table
    CREATE TABLE pilot_languages (
      pilot_id integer NOT NULL,
      language_id integer NOT NULL
    );
    
    -- Composite primary key
    ALTER TABLE pilot_languages ADD CONSTRAINT pilot_language_pkey PRIMARY KEY (pilot_id, language_id);
    -- The following fkey remains poorly named to avoid regressions related to psql naming
    ALTER TABLE pilot_languages ADD CONSTRAINT pilots_fkey FOREIGN KEY (pilot_id) REFERENCES pilots(id);
    ALTER TABLE pilot_languages ADD CONSTRAINT languages_fkey FOREIGN KEY (language_id) REFERENCES languages(id);

### install:
    # Install sqlboiler v4
    GO111MODULE=off go get -u -t github.com/volatiletech/sqlboiler
    # Install an sqlboiler driver - these are seperate binaries, here we are
    # choosing postgresql
    GO111MODULE=off go get github.com/volatiletech/sqlboiler/drivers/sqlboiler-psql

    # Do not forget the trailing /v4
    go get github.com/volatiletech/sqlboiler/v4
    # Assuming you're going to use the null package for its additional null types
    # Do not forget the trailing /v8
    go get github.com/volatiletech/null/v8

### generate:
    sqlboiler.toml 
    """
    wipe=true
    no-tests = true
    
    [psql]
        dbname = "postgres"
        host = "localhost"
        port = 5432
        user = "user"
        pass = "password"
        schema = "public"
    """
    
    sqlboiler psql
    
    
    //go:generate sqlboiler psql
    go generate
