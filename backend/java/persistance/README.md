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

### TRANSACTIONS PROPAGATION
- Required - default propagation strategy, it only starts a transaction if there is none yet
- Requires new - any current transaction context is suspended and replaced by a new transaction
- Supports - if current thread runs inside transaction this method will use it, otherwise it executes outside of a transaction context
- Not supported - any currently running transaction context is suspended and the current method runs outside of a transaction
- Mandatory - current method runs only if the current method is already associated with a transaction context
- Nested - current method is executed within a nested transaction if the current thread is already associated with a transaction. Otherwise a new transaction is started
- Never - the current method must always run outside of a transaction context, otherwise exception

### PESIMISTIC AND OPTIMISTIC LOCKING
- Pessimistic locking - database lock - once lock acuiqred no other transaction can interfere - good if can be released timely fashioned - prevents non-repeatable-reads, lost updates
- Optimistic locking - row version - read row version, during update use read version in where clause

### JPA ENTITY STATES
- New (transient) - not mapped yet to database row
- Managed (persistent - proxied by jpa, dirty checks
- Detached - once current persistence context is close all previously managed entities become detached (not tracked) 
- Removed - to be deleted during persistence context flush

### HIBERNATE RELATIONSHIPS
when using @ElementCollection prefer set over list - when removing in list hibernate needs to ensure it deletes proper element

- OneToMany - prefer bidirectional with methods (atleast ensure child has proper parent id set)
- OneToOne - prefer bidirectional, possibility of use @MapsId to share same key on both sides
- ManyToMany - prefer sets, bidirectional

### HIBERNATE
- explicit locks with version field
- implicit with @OptimisticLocking set with where clause using read fields

- pessimistic read lock - prevent any other transaction from acquiring exclusive lock
- pessimistic write - prevent any other transaction from acquirin shared/exclusive lock
