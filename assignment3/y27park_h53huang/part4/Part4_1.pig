REGISTER ToColumns.jar;
data = LOAD '$input' USING PigStorage(',');
maxData = FOREACH data GENERATE $0, (bag{tuple()}) TOBAG($1 ..) AS gene:bag{tuple()};
maxExpression = FOREACH maxData GENERATE $0, ToColumns(gene);
STORE maxExpression INTO '$output' USING PigStorage(',');
