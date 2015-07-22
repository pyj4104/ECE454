#!/bin/sh
ant
hdfs dfs -rm -r -skipTrash /user/y27park/output
hadoop jar dist/lib/SA.jar WordCount /user/y27park/input /user/y27park/output
