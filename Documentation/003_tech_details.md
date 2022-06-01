# Environment setup

* Eclipse Java EE IDE for Web Developers, version: Oxygen.3a Release (4.7.3a) Build id: 20180405-1200
* Intellij 2020

* JDK 17
* JavaFX 17.0.1
* Scene Builder
* MySQL Connector/J 8.0.23

## Installation

* Update MySQL connection info in DataBase.java
* If you're using Intellij, all libraries will be automaticly installed by Gradle
* To run project in Eclipse, you need manualy install JavaFX library and Mysql JDBC Driver to your project

## Compilation

* To compile project in Eclipse you need to set this as a VM arguments
  * ```--module-path "lib" --add-modules javafx.controls,javafx.fxml```
  * ```lib - path to JavaFX libary, for example - C:\Users\Admin\Desktop\JavaFX\lib```
* If you're using Eclipse, make sure that ```PATH_STATE``` in ```SceneController.java``` is set to ```true```

## Running .jar file
* First of all, in Command Line you need to open folder where .jar file located with ```cd``` command
* To run .jar file you need to start program via Command Line with this arguments
  * ```java --module-path "lib" --add-modules javafx.controls,javafx.fxml -jar name.jar```
  * Where ```lib``` - path to JavaFX libary, for example ```C:\Users\Admin\Desktop\JavaFX\lib```
  * Where ```name``` - version name, for example - ```v0.0.4```
* After all, you can use programm. To login as Admin use login ```0xAdmin``` and password ```admin``` 
 
## Database

* MySQL

To connect program to your database server, you need to change connections settings in ```DataBase.java``` at lines 31-43
If you are running .jar file, you will automaticly connect to my database server, so you don't need to recompile your program
