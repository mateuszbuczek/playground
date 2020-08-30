## Purpose of this module
This module will teach you how to use `go install` to compile and install packages.
We will learn the `go install` command and what needs to be provided to it.


## Verify locally
To test this module locally:
* Open up a terminal at the project root
* Run command `cd module4` to change to the `module4` directory
* Run command `go test -run Module4` to run all tests for module 4, or
* Run command `go test -v -run Module4` to run all tests for module 4 with verbose information

## Task 1: Get help information of `go install`
To compiles and installs the dependency packages, we use the [`go install` command](https://golang.org/cmd/go/#hdr-Compile_and_install_packages_and_dependencies).

The syntax can be as simple as this:
```
go install <package>
```

Same as `go get`, the above `package` can be the git repo of the package if hosted on github (that is, without the `https://` prefix).

To get more information on `go install`, check the output of `go help install`

To get more information about the build flags, check the output of `go help build`


## Task 2: Use `go install` to compile and install a package
Your task is to write a command to import the following package which can be used to provide common case conversion functions. 
Here is the `go.dev` link: (Note: you need to read and find out the value for `<package>`)
```
https://pkg.go.dev/github.com/codemodus/kace?tab=doc
```

In the terminal, type in the command to import the `kace`
