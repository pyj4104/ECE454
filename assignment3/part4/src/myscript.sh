#!/bin/sh
user=h53huang
dir=/user/$user
hdfs dfs -rm -r -skipTrash /user/$user/output
export CLASSPATH
CLASSPATH=$(hadoop classpath):/usr/hdp/current/pig-client/pig-0.14.0.2.2.4.2-2-core-h2.jar
#rm "\"pig_\"*"
cd myudf
javac Part3calc.java
jar -cf ../Part3calc.jar Part3calc.class
rm Part3calc.class
cd ..
pig -param input=/user/$user/input/ -param output=/user/$user/output part3.pig
