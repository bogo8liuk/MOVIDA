# MOVIDA

Movida is a project with educational purposes, as part of the exam of the course "Algoritmi e Strutture dati" at Alma Mater Studiorum University of Bologna. The idea behind MOVIDA is to apply various algorithms and data structures in order to

##### Authors

Luca Borghi, Stefano Cremona.

### Build

The project has been developed using openjdk-8. In order to install this version use the command below :
```bash
$ sudo apt-get install openjdk-8-jre
```
To build the project, move to the root directory MOVIDA/ then launch the command:

```bash
$ ./build.sh -b
```
The options "-b" | "-b=all" allow to compile all the files in the paths movida/commons/ and movida/borghicremona/.

The following options, allow to compile the files contained in the directory specified after the symbol "-b="
```bash
$ ./build.sh -b=borghicremona
```
```bash
$ ./build.sh -b=commons
```
```bash
$ ./build.sh -b=hashmap
```
```bash
$ ./build.sh -b=graph
```
```bash
$ ./build.sh -b=sort 
```
```bash
$ ./build.sh -b=bstree
```

The option "-u" unbuild the project, deleting all the files previously compiled.
```bash
$ ./build.sh -u
```

Alternatevely, users can compile files manually moving to the root directory of the project and calling the javac command as shown in the example below :
```bash
$ javac movida/borghicremona/MovidaCore.java
```

### Project Structure

We have 2 main modules: commons and borghicremona

#### commons/

External files, provided by professors.

#### borghicremona/

Files in this directory comprehend : 

- Assert.java : auxiliary class that allows to declare assertions;

- Dictionary.java : ccontains interfaces that our structures have to implement;

- Hash.java : 

- KeyValueElement.java : class implemented to represents elements that have both a key and a value;

- MovidaCoreTest.java : 

- MovidaCore.java : 

- Test.java : contains tests for the implementations of : hashmaps, binary search trees, non-oriented graphs and sort algorithms.

There are also 4 sub-directories :

##### sort/ 

Contains the implementation of the Vector class;

##### bstree/ 

Contains the implementation of the BinarySearchTree class;

##### graph/

Contains the implementation of the Graph class, includes auxiliary classes such as Arch.java, NodeFind.java, etc. ;

##### hashmap/

Contains the implementation of the HashMap class.
