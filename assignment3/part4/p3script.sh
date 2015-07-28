#!/bin/sh
user=h53huang
dir=/user/$user
hdfs dfs -rm -r -skipTrash /user/$user/1koutput
export CLASSPATH
CLASSPATH=$(hadoop classpath):/usr/hdp/current/pig-client/pig-0.14.0.2.2.4.2-2-core-h2.jar
rm pig_*.log
cd myudf
javac Part3calc.java
jar -cf ../Part3calc.jar Part3calc.class
rm Part3calc.class
cd ..
pig -param input=/user/$user/1kinput/ -param output=/user/$user/1koutput part3.pig
