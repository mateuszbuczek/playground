## Purpose of this module
This module will teach you how to use `go doc` to prints the documentation for a function
Similarly, the command can be used to print the documentation for a package, a variable, or a method.


## Verify locally
To test this module locally:
* Open up a terminal at the project root
* Run command `cd module6` to change to the `module6` directory
* Run command `go test -run Module6` to run all tests for module 6, or
* Run command `go test -v -run Module6` to run all tests for module 6 with verbose information

## Task 1: Add a comment in source code
A `go` source file named `module6.go` is provided, 
but the documentation for a function (`FunctionForModule6GoDoc`) is missing.

Complete the task by adding a comment in the source file in an editor.


## Task 2: Print out the added comment and direct to a file
Read the `go doc` command document from `go help doc` in the terminal/

Since `go doc` outputs to `stdout`, we need to redirect to a text file using the following format:
(Note that typically you do not need to have the redirection part, this is just for our verification)
```
go doc <methodName> > module6.txt
```

In the terminal, type in the complete `go doc` command.


Open `module6.txt` in an editor. 
Does it contain the documentation you added in Task 1?


