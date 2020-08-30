## Purpose of this module
This module focuses on the use of the standalone program called `goimports`,
which is used to import missing packages.


## Verify locally
To test this module locally:

- Open up a terminal at the project root
- Run command `cd module5` to change to the `module5` directory
- Run command `go test -run Module5` to run all tests for module 5, or 
- Run command `go test -v -run Module5` to run all tests for module 5 with verbose information 


## Task 1: Install the `goimports` command
Read the first paragraph in the documentation of [Command goimports](https://godoc.org/golang.org/x/tools/cmd/goimports).
Notice how `go get` is being used to download and install the `goimports` command.

In the terminal, type in the complete command to install `goimports`.

### troubleshooting
You might encounter the error in command line which says "goimports: command not found".

Make sure following environment variables are defined properly.
the way to set up the environment variables are depending on the operating system you are using (Windows, Linux, MacOS).

Following is the example `~/.zshrc` file on MacOS:
```
# for golang
export GOPATH="$HOME/go"
export PATH="$PATH:$GOPATH/bin"
```

Close and reopen the command line program for the above change to take effect.


## Task 2: Fix a specific package
In this task, you are provided with a `go` source file (`module5_code.go`) which uses the `net/http` package.

Note that the package is not imported.

Type in `goimports --help`, and examine the help info.

In the terminal, type in the `goimports` command to import the missing package.


## Task 3: Write back to source file
Note that the above command outputs to stdout, instead of writing back to the source file

In the help information from `goimports --help`, examine the flags section carefully.

Now, in the terminal, type in the complete `goimports` command (with flags) to import the package and write back to the source file (`module5_code.go`).


