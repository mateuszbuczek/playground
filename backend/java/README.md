Java structures:
 - list:
    - CopyOnWriteArrayList - thread safe variant of arraylist
    - ArrayList - uses array by default with size of 10
    - Vector - old list type used before ArrayList was created, is thread safe in comparison to ArrayList
    - Stack - child objects of vector, currently replaced by linkedlist
    - LinkedList - before sorting operation is converted internally to array
 - maps:
    - hashmap - by default 16 buckets, if many items inside one bucket it may get resized(and items reevaluated if algorithm based on size).
      Inside bucket objects are linked with linkedlist
    - linkedhashmap - based on 2 linkedlists (one local inside bucket and one global for ordered iterating)
    - hashtable - deprecated thread safe version of hashmap
    - treemap - deprecated, worse performance than linkedhashmap  

GraalVM
 - alternative JVM
 - alternative Java compiler
 - native compiler (no JVM required) - graalVM can compile both to bytecode or native system code (executable which contains jvm inside)
 - higher build time, lower ram usage, lower startuptime
