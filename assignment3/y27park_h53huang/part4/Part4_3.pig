REGISTER Part3calc.jar;
data1 = LOAD '$input' USING PigStorage(',');
--data2 = LOAD '$input' USING PigStorage(',');
fdata1 = FILTER data1 BY $0 != '';
fdata2 = FOREACH fdata1 GENERATE *;
crossdata = CROSS fdata1, fdata2 PARALLEL 2;
relationships = FOREACH crossdata GENERATE Part3calc($0..);
result = FILTER relationships BY $0 != '';
STORE result INTO '$output' USING PigStorage(',');
