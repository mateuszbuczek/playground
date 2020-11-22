#### goroutine
 - independently executing function, has its own stack.
 - Many goroutines can be run on one thread

### channel
 - provides connection between two goroutines, allowing them to communicate
 - synchronization
    - `<- c` - will wait for a value to be sent
    - `c <- val` - waits for receiver to be ready
 - it's also possible to create buffered channels which removes synchronization
 