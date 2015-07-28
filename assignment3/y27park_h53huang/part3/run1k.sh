#!/bin/sh
user=h53huang
hdfs dfs -rm -r -skipTrash /user/$user/1koutput
hadoop jar Part3.jar  Part3 /user/$user/1kinput/ /user/$user/1koutput
