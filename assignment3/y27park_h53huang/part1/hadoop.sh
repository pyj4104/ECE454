#!/bin/sh
ant
hdfs dfs -rm -r -skipTrash /user/y27park/output
hadoop jar part1.jar part1 /user/y27park/input/1k_Samples /user/y27park/output
