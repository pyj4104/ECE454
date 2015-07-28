#!/bin/sh
user=h53huang
hdfs dfs -rm -r -skipTrash /user/$user/output
hadoop jar Part3.jar  Part3 /user/$user/input/ /user/$user/output
