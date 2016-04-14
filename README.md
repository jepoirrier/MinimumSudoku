# MinimumSudoku, a Sudoku game starting with only 17 digits and having 1 solution

# Introduction

I don't need to give you an introduction about Sudoku, a now well-known board- and mind-game (see [its Wikipedia article for more details](http://en.wikipedia.org/wiki/Sudoku)). On the web, there are a lot of references to the game, you can even find strategies and algorithms to solve it and a lot of software allowing you to play Sudoku. I was very interested in [Gordon Royle](http://people.csse.uwa.edu.au/gordon)'s studies about "[Minimum Sudoku](http://people.csse.uwa.edu.au/gordon/sudokumin.php)" ...

Gordon Royle is interested in Sudoku games where you only have the minimal amount of digits and have a unique solution. Following his research, the minimal number of digits on a Sudoku board that has only one solution is 17. He collected more than 40,000 of such starting grids and [put them on his website](http://people.csse.uwa.edu.au/gordon/sudokumin.php) (with a nice link to an external solver).

I tried to make a UML model of what/how I think the game should be designed. This special kind of sudoku is easy to solve: there is only one solution. If the software know the solution, it has only to compare the number the player gave to the solution and that's it! And here, we are sure there is only one solution. The UML modelling is available in French (in the doc/ directory). If you are interested, this directory also contains the final class diagram (the one actually used in the game, based on the previous analysis).

So, I wrote the game and I also learn a lot (both about the game and about UML, Java and OOP).

**The goal of MinimumSudoku is to allow you to play to this kind of Minimum Sudoku game :-)**

Please note that I am not such a big fan of Sudoku; I just wrote the game and enjoyed playing some games with it. I'll be glad to hear from you and to receive your suggestions/bugs/... Feel free to contact me.

## Features

* The player can choose one of the > 40,000 minimal games from Gordon Royle's initial set (or let the software choose a random game)
* The software include the unique solutions for the > 40.000 games. Games were solved using [Gary McGuire'solver](http://www.math.ie/checker.html)
* Options: open a (random) game, validate a grid, hint, see the unique solution
* User interface is in French for the moment (but it shouldn't be a problem, see translation in the usage section below)
* You can run this software under MS-Windows, MacOS and GNU/Linux, provided you have the Java Runtime Engine, version 6 and above.

The Minimum Sudoku (on top, right) comes from [a xkcd comics](http://xkcd.com/c74.html), [xkcd](http://www.xkcd.com/) is "a webcomic of romance, sarcasm, math, and language".

## Screenshots

MinimumSudoku screenshots are available in the screenshots/ directory.

## Software

Download the latest version of MinimumSudoku fron the bin/ directory.

This directory contains the JAR file (the software you'll use) and a text file with all the games. You can run this software under MS-Windows, MacOS and GNU/Linux (see the usage section below), provided you have the Java Runtime Engine, version 6 and above. You can [download it for free](http://www.java.com/en/) and install it.

This software is released under the GNU General Public Licence (GPL): Java source code is in the src/ directory.

Copyright (C) belongs to Jean-Etienne Poirrier, 2007. You can contact me at jepoirrier "at" gmail.com. Please report if you have any problem, comment or if you would like new features in this software.

## Usage

The software is so simple that there should be no problem to use it ... You can launch MinimumSudoku by double-clicking on the minimumsudoku.jar file (you can also enter this command in a shell: "java -jarminimumsudoku.jar"). Then, simply open a game by clicking on the "Ouvrir" button. A translation of all the buttons labels is in the table below.

| French        | English    | Description |
|---------------|------------|-------------|   
| Ouvrir        | Open       | opens a window where you can select a game | 
| Valider       | Validate   | validates the actual game | 
| Aide          | Hint       | gives you a hint (fill a random location) | 
| Solution      | Solution   | gives the unique solution (fill the whole grid) | 
| Quitter       | Quit       | quit the game | 
| Sélectionner  | Select     | selects the chosen game (with the slider) | 
| Au hasard     | Random     | gives a random game | 
| Annuler       | Cancel     | cancel the game selection | 
| Grille valide | Valid grid | your grid is valid :-) | 

I'll try to add the English translation of the game, one day (please remind me if you want it ;-)).

