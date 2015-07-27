REGISTER Part3calc.jar;
data1 = LOAD '$input' USING PigStorage(',');
data2 = LOAD '$input' USING PigStorage(',');
crossdata = CROSS data1 data2;
relationships = FOREACH crossdata GENERATE Part3calc($0..);
STORE relationships INTO '$output' USING PigStorage(',');
