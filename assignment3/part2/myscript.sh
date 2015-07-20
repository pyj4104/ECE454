#!/bin/sh
user=h53huang
hdfs dfs -rm -r -skipTrash /user/$user/output
hadoop jar Part2.jar  Part2 /user/$user/input/ /user/$user/output
