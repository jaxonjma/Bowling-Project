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
* Spring Boot 
* JUnit 5
* Mockito
* Gradle

...

**ABOUT THE SOLUTION**

This command-line solution implements a system of controlled logs and exceptions, it is an application that despite being monolithic is fully scalable and based on SOLID principles, all its configuration is done in the file "/bowling/src/main/resources/application.properties" and has been validated with SonarLint as static code analyzer.

The final packaging of the application is a .Jar file.

The application receives a parameter with the path to a file with the result of a bowling game and prints them in the requested format.

To achieve the above, just run the .jar generated (Available in Documentation_Folder/bowling.jar) from the command console by doing: "java -jar path/to/jar path/to/scores.txt"
