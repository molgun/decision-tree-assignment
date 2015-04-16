# Decision Tree Assignment
This repository contains Firat University, Computer Engineering, Advanced Machine Learning course assignment source codes.

I released v0.1-SNAPSHOT version. You can download jar file from [here](https://github.com/molgun/decision-tree-assignment/releases/tag/v0.1).

If you want to build it yourself, simply run 
```
mvn clean install
```
after the clone and you will have your jar file under target directory.

If you want to execute the jar file run this command from command line
```
java -jar decision-tree-assignment-0.1-SNAPSHOT-jar-with-dependencies.jar
```
then it will ask you the property values. If you type your values then it will give results to you.

TODO
* Numeric property support for Entropy Gain
* Nominal property support for Gini Gain
* Dynamic gain selection
* Adaptive input
* cross-validation support
* Refactoring Tree (it's awful right now)
