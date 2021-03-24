Propagation:
    - REQUIRED - if transaction exists use it otherwise create one
    - SUPPORTS - if transaction exists use it otherwise do not use transaction
    - MANDATORY - if transaction exists use it otherwise throw exception
    - NEVER - if transaction exists throw exception otherwise proceed without transaction
    - NOT_SUPPORTED - if transaction exists suspend it and proceed without transaction
    - REQUIRES_NEW - if transaction exists suspend it and create new otherwise create new
    - NESTED - if transaction exists create savepoint (rollback to this moment) otherwise create new
Isolation:
    - READ_UNCOMMITTED - transaction can read from uncommitted data of other concurrent transactions (unsupported by postgres/oracle)
    - READ_COMMITTED - uncommitted changes on concurrent transaction no impact (default for postgres/oracle) 
    - REPEATABLE_READ - does not allow simultaneous access to row at all (lost update cant happen) (not supported by oracle)
    - SERIALIZABLE - do not execute calls concurrently (enqueue calls and execute them in serial)
