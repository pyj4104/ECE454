/*
  Code copied from https://github.com/facebookarchive/hadoop-20/blob/master/src/examples/org/apache/hadoop/examples/WordCount.java
*/

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Part1 {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, Text>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      Double maxVal = 0.0;
      String[] strList = value.toString().split(",");
      ArrayList<String> retVal = new ArrayList<String>();
      for(int i = 1; i < strList.length; i++)
      {
	Double val = Double.valueOf(strList[i]);
        if(val.equals(maxVal))
        {
          retVal.add("gene_" + String.valueOf(i));
        }
        else if(val > maxVal)
        {
          retVal.clear();
          maxVal = val;
          retVal.add("gene_" + String.valueOf(i));
        }
      }
      retVal.add(0, strList[0]);
      word.set(strList[0]);
      context.write(new Text(String.valueOf(retVal).replace("[", "").replace("]", "").replace(" ", "")), new Text("")); 
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: Part 1 <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "Part 1");
    job.setJarByClass(Part1.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setNumReduceTasks(0);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
