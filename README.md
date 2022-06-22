# Same Game

The __Same__ game consist of a cell's grid of different colors that the user has to consume by clicking on it 
until no more moves are allowed. The goal is to empty the grid while obtaining the highest possible score.

This application can be executed in a terminal session or in a GUI. The GUI has been made with JavaFX.

![SameGame logo](/ressources/sameGame-pic.jpg)

## Getting started

### Makefile
------------

The project __makefile__ defines several directives:

* build (to compile and generate the project's jar file) 
* exec-c (to run the terminal application)
* exec-fx (to run the GUI application)
* doc (to generate the documentation under `doc/`)
* clean (to clean the `target/` directory)

To run the application you can use the makefile's directives as the following examples
shows:

To run it in the terminal:
```
[~/56080-atlg3/SameGame] make
[~/56080-atlg3/SameGame] make exec-c
```

To run it using the GUI
```
[~/56080-atlg3/SameGame] make exec-fx
```

You can also generate the documentation of the project using the following command:
```
[~/56080-atlg3/SameGame] make doc
```

### Maven
---------

It is also possible to run the application using the __maven__ project manager combined with 
some command using the java interpreter as the following examples demonstrates:

To run it in the terminal:
```
[~/56080-atlg3/SameGame] mvn compile
[~/56080-atlg3/SameGame] java -cp target/classes g56080.same.AppConsole
--------------- Or
[~/56080-atlg3/SameGame] mvn build
[~/56080-atlg3/SameGame] java -jar target/SameGame-1.0.jar
```

And using the GUI:
```
[~/56080-atlg3/SameGame] mvn javafx:run
```

### Using the javac and java terminal tools
-------------------------------------------

Finally, it is also possible to run the application only using the
__command line tools__ provided by the JDK as the following examples shows:

To run it in the terminal:
```
[~/56080-atlg3/SameGame] javac @gen-cls --module-path $JFXHOME
[~/56080-atlg3/SameGame] java -cp target/classes g56080.same.AppConsole
```

And using the GUI:
```
[~/56080-atlg3/SameGame] javac @gen-cls --module-path $JFXHOME
[~/56080-atlg3/SameGame] java --module-path $JFXHOME --add-modules javafx.controls,javafx.graphics g56080.same.AppFX
```

The _$JFXHOME_ is the path to the `lib/` directory of your javafx
installation. It should contain the appropriate jar files.

## Dependencies

The minimal dependencies of this application are the java, javac and javadoc tools provided
by a fresh installation of JDK. You can also manage and run the application using the maven 
project manager or directly through the makefile's directives.

JDK 16 is required by the application to work properly. Moreover, the execution environment
for the terminal application must support the ANSI standard for the color to be displayed.

## Notes

If you are using NetBeans IDE to run the terminal application in a Windows environment, make sure 
to install __cygwin__ before executing it. Then you can click on the `tools` option present in the
menu bar up above and select `Open in Terminal` to execute the application following the above
directives.

Enjoy !

