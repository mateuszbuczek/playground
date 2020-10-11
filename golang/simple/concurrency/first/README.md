thread - have own execution stack,
fixed stack space,
managed by OS

goroutines - enable concurrent programming, 
have own execution stack,
variable stack space, 
managed by go runtime - it also creates threads (not that many as thread base concurrency),
 but creates even more goroutines which it assigns properly to limited amount of threads

sync package - allow go routines to coordinate their work -
 wgroup - wait 
 mutex - shared memory 

channels - provide a safe way for goroutines to communicate
 coordination
 working with shared data
