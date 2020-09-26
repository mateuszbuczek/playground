#### 1. Just in time compilaton and the code cache
    - Java compiles .java code to byte code .class which is then loaded into JVM
    - JVM is interpreting byte code
    - Just in time compilation - used to improve performance of running code. 
    
      Most often called part of code/methods can be compiled to native system code to speed up execution.
      Then on JVM there will be part of byte code and part of native code, the latter will be executed faster.
      That means code runs faster the longer it is left to run. 
      Process of compiling byte code to native code runs on different thread.
      JIT compiled native code can be put to jvm code cache to execute most often used code even faster.
    - There are 2 JVM JIT compilers c1 (tier 1,2,3, fast compiling) and c2 (tier 4, slower, higher optimization). 
      The process of deciding by what JIT compilator should the part of code be compiled is called profiling

#### 32 bit vs 64 bit
    - might be faster if heap < 3GB                   | necessary if heap > 4GB
    - max heap size = 4 GB (2^32)                     | Max heap size - OS dependent
    - every native pointer takes 4 bytes              | 8 bytes
    - client JIT compiler (small short running app)   | client & server JIT compilers (long running apps) 

#### Java memory structure
    - the stack
        - every thread has its own stack
        - stacks works as first in last out
        - stack contains local variables(small data types)/object-references which are easy to pop off (clean)
    - the heap
        - is shared across all threads
        - store objects (accessed through reference by stack)
        - contains string pool (containins strings objects put by compiler, 
          if string is created by 'new' keyword it's not being put here (it may be using intern method on newly created string))).
          Strings in string pool can be garbage collected.
          It does work internally as hashmap.
          Has default fixed size of buckets.
    - metaspace
        - store general metadata ( for example which classes where compiled by JIT to native code ) 
        - store information where static vars (objects) are stored (primitives are stored in metaspace itself) - static vars will never be garbage collected
    
    - exceptions:
        - if object is used in small scope and will be not used later on (will be cleaned pretty soon)
         modern JVM's for efficiency can create those objects on stack
        
#### escaping references
    - immutable collection
    - instead of creating new copy objects use interfaces with getters only
    - java 9 modules ( do not import implementation, just interface)
    - primitive type wrappers are immutable (when other value assigned new object created)

#### FLAGS
    - debugging
        - -XX:+PrintCompilation - prints to console jit code compilation stats ( after program exit 1)
        - -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation - show jit code compilation stats at runtime (through log file)
        - -XX:+PrintCodeCache - get information about available/used jvm cache size
        - -XX:+PrintFlagsFinal - get information final flags used
        
        - -XX:CompileThreshold=n - number of times of `part of code/method` execution after which this `part of code/method` gets JIT compiled to native code
        - -XX:CICompilerCount=n - threads that may be used for JIT compilation. default 3

        - -XX:+PrintStringTableStatistics - information about string pool (number of buckets, entries, literals etc.)
        - -XX:StringTableSize=n(must be prime number) - size of string pool hash table (number of buckets)

        - -XX:MaxHeapSize=n | -Xmx<n> - set maximum heap size
        - -XX:InitialHeapSize=n - | -Xms<n> - set initial heap size

#### Garbage collector
    - takes care of free up memory
    - objects that are no longer accessible from stack or metaspace (are unreachable) are eligible for garbage collection
    - some GC's during gc process are freeing up memory to system.
    - process of garbage collecting is resource consuming process and may slow program for a while.
    - when gc is removing object from the heap it;s calling finalize method on it (which is deprecated)
    - soft leak - when an object remains referenced when no longer needed
    - JavaVisualVM to check current jvm stats (current thread usage/ heap space used etc.) 
