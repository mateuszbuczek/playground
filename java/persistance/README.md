### Database scaling
- scaling out (horizontally) 
    - costs of distributed system maintain.
    - more resources than scaling up for same price.
    - costs may grow if each machine will require its own database usage license
- scaling up (vertically)
    - easy to maintain
    - one machine one license
    - single point of failure
    - less resources for same price compared to scaling out
    
### Database query execution
- parser - parse
- optimizer - create or use cached execution plan
- executor - execute

### ACID
- Atomicity - grouping all operations into one atomic (possible to rollback)
- Consistency - all commited transactions leave the database in a proper state
- Isolation - two phase locking (2PL) (shared read lock or exclusive write lock) / multi version concurrency control (MVCC)
    - dirty write - two concurrent transactions are allowed to modify the same row at the same time
    - dirty read - transaction is allowed to read uncommited changes of other transaction
    - non repeatable read / fuzzy reads - transaction reads row without shared lock then other transaction can change it in the meantime
    - phantom read - two consecutive query executions render different results because a concurrent transaction has modified the range of records in between the two calls.
    - read skew - watching two tables, read first table, first and second tables changed , read second table
    - write skew
    - lost update - first transaction read, second update, first update
