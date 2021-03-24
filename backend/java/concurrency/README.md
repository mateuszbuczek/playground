<b>Concurrency</b> is the ability to do more than one thing at the same time

multiple tasks are making progress during the same period of time

it is not parallelism.

one cpu can execute at most one task

<b>Levels of concurrency</b>
- <b>Multiprocessing</b> - Multiple Processors/CPUs executing concurrently. The unit of concurrency here is a CPU.

- <b>Multitasking</b> - Multiple tasks/processes running concurrently on a single CPU. The operating system executes these tasks by switching between them very frequently. The unit of concurrency, in this case, is a Process.

- <b>Multithreading</b> - Multiple parts of the same program running concurrently. In this case, we go a step further and divide the same program into multiple parts/threads and run those threads concurrently.

<b>Process</b>
A Process is a program in execution. It has its own address space, a call stack, and link to any resources such as open files.

A computer system normally has multiple processes running at a time. The operating system keeps track of all these processes and facilitates their execution by sharing the processing time of the CPU among them.

<b>Thread</b>
A thread is a path of execution within a process. Every process has at least one thread - called the main thread. The main thread can create additional threads within the process.

Threads within a process share the process’s resources including memory and open files. However, every thread has its own call stack.

Since threads share the same address space of the process, creating new threads and communicating between them is more efficient.

<b>Problems</b>
- race condition 
- lock - deadlock/starvation
- memory consistency errors
