# MOVIDA

Movida is a project with educational purposes, as part of the exam of the course "Algoritmi e Strutture dati" at Alma Mater Studiorum University of Bologna. The idea behind MOVIDA is to apply various algorithms and data structures in order to interact with a knowledge-base about movies.

##### Authors

Luca Borghi, Stefano Cremona.

### Build

The project has been developed using openjdk-8.
To build the project, move to the root directory MOVIDA/ and follow the guide:

When you will build the project, some warnings are given by the java compiler, just ignore them, they are generated
by safe casts in the code.

The options "-b" and "-b=all" allow to compile all the files in the paths movida/commons/ and movida/borghicremona/.
```bash
$ ./build.sh -b
```

```bash
$ ./build.sh -b=all
```

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

Alternatevely, users can compile files manually moving to the root directory of the project and execute the javac command as shown in the example below :
```bash
$ javac movida/borghicremona/MovidaCore.java movida/borghicremona/Test.java movida/borghicremona/MovidaCoreTest.java
```

If you want to run one of the test, move to the root directory of the project and execute one of the following command:
```bash
$ java movida.borghicremona.Test
```

```bash
$ java movida.borghicremona.MovidaCoreTest
```

### Project Structure

We have 2 main modules: commons and borghicremona

#### commons/

External files, provided by professors.

#### borghicremona/

Files in this directory comprehend : 

- KeyValueElement.java : class that represents an element that have both a key and a value;

- Dictionary.java : the interface that our structures (HashMap and BinarySearchTree) have to implement;

- Assert.java : auxiliary class that allows to call some assertions;

- Hash.java : class that offers a hash function and a method of growing to classes that uses hashmap implemented
with an array of KeyValueElement;

- Test.java : tests for the implementations of : hashmaps, binary search trees, non-oriented graphs and sort algorithms;

- MovidaCore.java : core class of the project that represents a knowledge-base about movies;

- MovidaCoreTest.java : tests for MovidaCore.

There are also 4 sub-modules :

#### sort/ 
Contains Vector.java, namely a class that encapsulates an array of generics and it offers methods like selectionSort,
quickSort and shuffle.

#### bstree/
Contains BinarySearchTree.java, namely a class that implements a binary search tree; it implemets the Dictionary interface.

#### graph/
Contains the implementation of the Graph interface, including also Arch.java (representing the arch of a graph) and other
useful facilities for graphs.

#### hashmap/
Contains HashMap.java, namely a class that implements a open addressing hashmap.

ATTENTION: NonOrientedGraph and HashMap use only keys with type String, do not use other types of key.
