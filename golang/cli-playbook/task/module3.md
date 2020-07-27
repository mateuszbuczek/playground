## Purpose of this module
This module will teach you how to use `go get` to download and install packages.
We will learn the `go get` command and what needs to be provided to it.


## Verify locally
To test this module locally:
* Open up a terminal at the project root
* Run command `cd module3` to change to the `module3` directory
* Run command `go test -run Module3` to run all tests for module 3, or
* Run command `go test -v -run Module3` to run all tests for module 3 with verbose information

## Task 1: Get help information for `go get`
The [`go get` command](https://golang.org/cmd/go/#hdr-Add_dependencies_to_current_module_and_install_them) will be used to download and install packages.
The syntax can be as simple as this:
```
go get <package>
```

The above `package` can be the git repo of the package if hosted on github (that is, without the `https://` prefix).

To get more information, check the output of `go help get`.

To list all packages in the module, you can use `go list -m all`. 
Help on `list` can be found at `go help list`


## Task 2: Use `go get` to install a package
To add dependencies to current module and install the packages, we use the `go get` command.

Your task is to write a command to import the following package which can be used to retrieve the sunrise and sunset time given a location and a date. Here is the github repo:
```
https://github.com/nathan-osman/go-sunrise
```

In the terminal, type in the complete command to import the above package.



