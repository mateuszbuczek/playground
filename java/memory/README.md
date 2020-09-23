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

#### Java memory
    - the stack
        - every thread has its own stack
        - stacks works as first in last out
        - stack contains local variables(small data types)/object-references which are easy to pop off (clean)
    - the heap
        - is shared across all threads
        - store objects (accessed through reference by stack)

#### escaping references
    - immutable collection
    - instead of creating new copy objects use interfaces with getters only
    - java 9 modules ( do not import implementation, just interface)
    - primitive type wrappers are immutable (when other value assigned new object created)

#### FLAGS
    - debugging
        - -XX:+PrintCompilation - prints to console jit code compilation stats ( after program exit 1)
        - -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation - show jit code compilation stats at runtime (through log)
        - -XX:+PrintCodeCache - get information about available/used jvm cache size
        - -XX:+PrintFlagsFinal - get information final flags used
        
        - -XX:CompileThreshold=n - number of times of `part of code/method` execution after which this `part of code/method` gets JIT compiled to native code
        - -XX:CICompilerCount=n - threads that may be used for JIT compilation. default 3

