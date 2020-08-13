## Objective
Write a program that manages disjointed intervals of integers. 
E.g.: [[1, 3], [4, 6]] is a valid object gives two intervals. 
      [[1, 3], [3, 6]] is not a valid object because it is not disjoint. 
      [[1, 6]] is the intended result.

Empty array [] means no interval, it is the default/start state.

Implement a program that read the inputs from a file and outputs them to another file. 
These files would be specified as command line parameters in the following format:

`./executable input_file.csv output_file.txt`

The input file would be a CSV in the following format:

`action, start of interval, end of interval`

Here is an example sequence in an input file:
```
add, 1, 5
remove, 2, 3
add, 6, 8
remove, 4, 7
add, 2, 7
```

The output file would contain the state of the intervals at each step of the sequence. 
Assume that the output array is initially []. 
The following is the expected content of the output file for the above example input sequence.

```
[[1, 5]]
[[1, 2], [3, 5]]
[[1, 2], [3, 5], [6, 8]]
[[1, 2], [3, 4], [7, 8]]
[[1, 8]]
```

## Technologies Used
* Java 8 (jdk1.8.0_201)
* Gradle 6.5.1
* IntelliJ IDEA 2020.2
* JUnit Jupiter 5.4.1
* Hamcrest 2.1
* Commons Collections 4.3
* Commons IO 2.
* Commons CSV 1.8

## Usage
1. Clone [repo](https://github.com/jgillson/disjointed-intervals-manager)
2. Run `./gradlew run` (The first run will download the gradle wrapper and any associated dependencies).

Example:
```
./gradlew run
Downloading https://services.gradle.org/distributions/gradle-6.5.1-bin.zip
.........10%..........20%..........30%..........40%.........50%..........60%..........70%..........80%.........90%..........100%

Welcome to Gradle 6.5.1!

Here are the highlights of this release:
 - Experimental file-system watching
 - Improved version ordering
 - New samples

For more details see https://docs.gradle.org/6.5.1/release-notes.html

Starting a Gradle Daemon, 1 stopped Daemon could not be reused, use --status for details

> Task :run
>
```

3.  At the prompt under `> Task :run`, type the path to the `input_file.csv` and the `output_file.txt`.  
If the latter file doesn't exist, it will be automatically created.

Example:

```
<=========----> 75% EXECUTING [7s]
> :run
/Users/john.gillson/Documents/input_file.csv /Users/john.gillson/Documents/output_file.txt

[[1, 5]]
[[1, 2], [3, 5]]
[[1, 2], [3, 5], [6, 8]]
[[1, 2], [3, 4], [7, 8]]
[[1, 8]]
```

Navigate to the `output_file.txt` and you should see the above output in the file.

## Testing
JUnit Jupiter was used as the testing framework.
To run all the tests from the command line, run `./gradlew clean test`

Note: the `input_file.csv` and `output_file.txt` can also be run from the `DisjointedIntervalsFileHandlerTest`

Example:
```
./gradlew clean test

> Task :test
Running test: Test testAddUnionIntervalWithMinOrMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testAddUnionIntervalWithMinOrMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2]]


DisjointedIntervalsManagerTest > Tests adding if there's a union with the new interval and the current min or max interval STANDARD_OUT
    [[1, 2]]
Test: Test testAddUnionIntervalWithMinOrMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 4]]

    [[1, 2], [3, 4]]
Test: Test testAddUnionIntervalWithMinOrMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 4], [7, 8]]

    [[1, 2], [3, 4], [7, 8]]
Test: Test testAddUnionIntervalWithMinOrMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 8]]

    [[1, 8]]
Running test: Test testValidActionInput()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5]]


DisjointedIntervalsManagerTest > Tests the whole thing STANDARD_OUT
    [[1, 5]]
Test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 5]]

    [[1, 2], [3, 5]]
Test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 5], [6, 8]]

    [[1, 2], [3, 5], [6, 8]]
Test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 4], [7, 8]]

    [[1, 2], [3, 4], [7, 8]]
Test: Test testWholeThing()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 8]]

    [[1, 8]]
Running test: Test testValidStartAndEndIntervalValues()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testRemoveEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testRemoveEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[6, 13]]


DisjointedIntervalsManagerTest > Tests removing if the current interval is inside the interval to remove STANDARD_OUT
    [[6, 13]]
Test: Test testRemoveEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[6, 13], [15, 16]]

    [[6, 13], [15, 16]]
Test: Test testRemoveEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[6, 13], [15, 16], [17, 18]]

    [[6, 13], [15, 16], [17, 18]]
Test: Test testRemoveEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[6, 13], [17, 18]]

    [[6, 13], [17, 18]]
Running test: Test testInvalidActionInput()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testStartValueLessThanEndValue()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testAddEndIntervalLessThanMinInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testAddEndIntervalLessThanMinInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[6, 8]]


DisjointedIntervalsManagerTest > Tests adding if the new end interval is less than the current min interval STANDARD_OUT
    [[6, 8]]
Test: Test testAddEndIntervalLessThanMinInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5], [6, 8]]

    [[1, 5], [6, 8]]
Running test: Test testInvalidStartAndEndInput()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testValidActionInputWithCaseSensitivity()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testAddIntervalFirstTime()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testAddIntervalFirstTime()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5]]


DisjointedIntervalsManagerTest > Tests adding an interval for the first time STANDARD_OUT
    [[1, 5]]
Running test: Test testRemoveStartIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testRemoveStartIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2]]


DisjointedIntervalsManagerTest > Tests removing if the new start interval is inside the current interval STANDARD_OUT
    [[1, 2]]
Test: Test testRemoveStartIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 5]]

    [[1, 2], [3, 5]]
Test: Test testRemoveStartIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 5], [6, 8]]

    [[1, 2], [3, 5], [6, 8]]
Test: Test testRemoveStartIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 4], [7, 8]]

    [[1, 2], [3, 4], [7, 8]]
Running test: Test testInvalidActionCommand()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testStartAndEndValueMustBePositive()(com.yourmechanic.DisjointedIntervalsManagerTest)
Running test: Test testRemoveStartAndEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testRemoveStartAndEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5]]


DisjointedIntervalsManagerTest > Tests removing if the new start and end interval is inside the current interval STANDARD_OUT
    [[1, 5]]
Test: Test testRemoveStartAndEndIntervalInsideCurrentInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 2], [3, 5]]

    [[1, 2], [3, 5]]
Running test: Test testAddStartIntervalGreaterThanMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest)
Test: Test testAddStartIntervalGreaterThanMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5]]


DisjointedIntervalsManagerTest > Tests adding if the new start interval is greater than the current max interval STANDARD_OUT
    [[1, 5]]
Test: Test testAddStartIntervalGreaterThanMaxInterval()(com.yourmechanic.DisjointedIntervalsManagerTest) produced standard out/err: [[1, 5], [6, 8]]

    [[1, 5], [6, 8]]
Running test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest)
Test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest) produced standard out/err: [[1, 5]]


DisjointedIntervalsFileHandlerTest > testHandleFiles() STANDARD_OUT
    [[1, 5]]
Test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest) produced standard out/err: [[1, 2], [3, 5]]

    [[1, 2], [3, 5]]
Test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest) produced standard out/err: [[1, 2], [3, 5], [6, 8]]

    [[1, 2], [3, 5], [6, 8]]
Test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest) produced standard out/err: [[1, 2], [3, 4], [7, 8]]

    [[1, 2], [3, 4], [7, 8]]
Test: Test testHandleFiles()(com.yourmechanic.DisjointedIntervalsFileHandlerTest) produced standard out/err: [[1, 8]]

    [[1, 8]]

BUILD SUCCESSFUL in 8s
5 actionable tasks: 5 executed
```
