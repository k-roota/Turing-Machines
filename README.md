# Turing machines interpreter and translator

The application allows you to run a one-tape Turing machine specified by transitions stored in a file or translate a two-tape Turing machine to its one-tape equivalent. 

## Compilation

In order to compile the project use `make` in the main directory. It runs Scala Build Tool which downloads necessary plugins and Scala version.

## Usage

The interpreter can be run as stated below:
```bash
./interpreter <path> <steps>
```
<path> is a path to a one-tape Turing machine and <steps> is a maximum number of steps which the machine will take. When the program is started it waits for passing input word (in a form of a number) to the standard input. If the machine accepts the word before <steps> limit, it writes "YES" to the standard output, otherwise it writes "NO".

The translator can be run as follows:
```bash
./translate <path>
```
Here <path> is a path to a two-tape Turing machine. As a result, the program outputs transitions of the equivalent one-tape machine.

## Turing machines file structure

Every line in a file describing a machine represents one transition. One-tape transitions are in the following form:
```
<current_state> <current_symbol> <new_state> <new_symbol> <new_direction>
```
In two-tape transition there are two values for <current_symbol> <new_symbol> and <new_direction>. <new_direction> is letter L, R or S meaning "move left", "move right", "stay". Symbols are natural numbers, states are strings. "start" is a starting state, "accept" and "reject" are terminal states.