## Purpose of this module
This module will teach you how to use `go generate` to scan for directives, run commands, and modify existing `*.go` source files.


## Verify locally
To test this module locally:
* Open up a terminal at the project root
* Run command `cd module7` to change to the `module7` directory
* Run command `go test -run Module7` to run all tests for module 7, or
* Run command `go test -v -run Module7` to run all tests for module 7 with verbose information

## Task 1: Get help for `go generate`
The command to get more information on `go generate` is `go help generate`

In a nutshell, `go generate` scans the files for lines like the following (called `directive`), and execute those commands:
```
//go:generate command <argument...>
```

Note that `go generate` must be run explicitly.


## Task 2: Fill in the directive
First, check the content of the provided `module7.go` file.

Then, in the terminal, run `go generate` and examine the output.
Note that the `echo` command is executed.

Third, check the content of the provided `module7_code.go` file.
Note the `fmt` and `runtime` packages are missing.

Your task is to fill in the directive to import the missing packages.
You need to replace the whole `echo "command and arguments"` part with proper command.

## Task 3: Run `go generate` to import the packages
With task 2 finished, in the terminal, type in the proper command, 
which calls the command line tool to import the missing packages.

Examine the content of `module7_code.go`. Does it properly import the packages?
