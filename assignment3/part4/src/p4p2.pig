REGISTER part2.jar;
data = LOAD '$input' USING PigStorage(',');
maxData = FOREACH data GENERATE $0, (bag{tuple()}) TOBAG($1 ..) AS gene:bag{tuple()};
maxExpression = FOREACH maxData GENERATE FLATTEN(part2(gene));
uniq = GROUP maxExpression BY $0;
uniq = FOREACH uniq GENERATE $0, $1.$1;
uniq = FILTER uniq BY $1;
STORE uniq INTO '$output' USING PigStorage(',');
