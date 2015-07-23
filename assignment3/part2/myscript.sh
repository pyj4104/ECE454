#!/bin/sh
user=y27park
hdfs dfs -rm -r -skipTrash /user/$user/output
hadoop jar Part2.jar  Part2 /user/$user/input/1.txt /user/$user/output
