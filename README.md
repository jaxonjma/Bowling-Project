**BOWLING PROJECT**

This is a project designed from the requirements available in the file **"java-challenge-bowling.pdf"** in **"Documentation_Folder/"**.

...

**ASSIGNMENT**

The goal of this exercise is to demonstrate your ability to build a greenfield project, specifically a
command-line application to score a game of ten-pin bowling
(https://en.wikipedia.org/wiki/Ten-pin_bowling#Rules_of_play).

The code should handle the bowling scores rules described in the specs and here:
https://www.youtube.com/watch?v=aBe71sD8o8c

...

**TECHNOLOGICAL STACK**

* Java 8
* Spring Boot 2.0.3.RELEASE
* H2 Database (Oracle based)
* JPA
* Hibernate
* JUnit
* Java Timer
* Gradle

...

**ABOUT THE SOLUTION**


**ABOUT THE ARCHITECTURE**

This solution implements a system of controlled logs and exceptions, it is an application that despite being monolithic is fully scalable and based on SOLID principles, all its configuration is done in the file "/bowling/src/main/resources/application.properties" and has been validated with SonarLint as static code analyzer.

The application does not make disk persistence, since it occupies a database in Oracle-based H2 memory.

As for the tests, it was not possible to work with TDD due to time constraints, but all the processes are organized for easy development of the same (Examples available in the application).

The final packaging of the application is a .WAR file.

The solution is divided into two major processes:

* ***Upload of files associated with a process:***
Responsible for taking any plain text file (.txt) from a parameterizable folder and persisting in a database in the "GAME" table associated with a loading process in the "PROCESS" table.

  This process starts when an item in the configured folder is placed.

  (When you upload an incorrectly formatted file you will get a message "The uploaded file is not a .txt")

* ***Print results:***
Responsible for taking the loading processes ordered from "START_DATE" (as long as they have not been processed) every few seconds (also configurable) and print the score results for 1 or N players.


**ABOUT THE CONFIG TO RUN**

To execute the solution you must download the source code of this project, import it to the IDE of your choice (Designed on STS), adjust the parameters available in the "/bowling/src/main/resources/application.properties" to your liking, execute the application as "Spring Boot App" and upload files with game results.

The solution execution process is shown in the video "/bowling/Documentation_Folder/Bowling test.mp4" also available in this repository, as well as a set of test files used during development in "/bowling/Documentation_Folder/Files to test.zip "


**ABOUT THE MODEL AND PERSISTENCE**

The Bowling Project uses a JPA-based persistence model as a Hibernate implementation and a "Writer" model that seeks to illustrate another form of mass persistence.

The entities of the solution are the following:

* ***PROCESS***: This entity keeps a record of each of the charges processed with its start date, end date, number of records, status, date of termination and error of having presented.

  His relationship with the entity "GAME" is one to many.

* ***GAME***: This entity keeps each of the records of the txt file associating them with a player, a result, a frame and a process; It will also store the total points per frame when the results are to be printed.

  Its relationship with the entity "PROCESS" is many to one.




