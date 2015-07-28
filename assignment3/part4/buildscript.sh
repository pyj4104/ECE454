#!/bin/sh
export CLASSPATH
CLASSPATH=$(hadoop classpath):/usr/hdp/current/pig-client/pig-0.14.0.2.2.4.2-2-core-h2.jar
rm pig_*.log
cd myudf
javac ToColumns.java
jar -cf ToColumns.jar ToColumns.class
rm ToColumns.class
javac part2GetGene.java
javac part2Calculate.java
jar -cf part2GetGene.jar part2GetGene.class
jar -cf part2Calculate.jar part2Calculate.class
rm part2GetGene.class
rm part2Calculate.class
javac Part3calc.java
jar -cf ../Part3calc.jar Part3calc.class
rm Part3calc.class
