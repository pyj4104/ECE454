/*
*/

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Part2 {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, DoubleWritable>{
    
    private DoubleWritable geneValue = new DoubleWritable();
    private Text geneID = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString(),",");
	  int geneNumber = 0;
      //System.out.println("Start mapping");
      while (itr.hasMoreTokens()) {
		if(geneNumber == 0){
			itr.nextToken();
			geneNumber++;
			continue;
		}
       	geneValue.set(Double.parseDouble(itr.nextToken()));
        geneID.set("gene_"+geneNumber);
        //System.out.println("writing gene_"+geneNumber+" "+String.valueOf(geneValue));
        geneNumber++;
        context.write(geneID, geneValue);
      }
    }
  }
  
  public static class GeneReducer 
       extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
    private DoubleWritable result = new DoubleWritable();

    public void reduce(Text key, Iterable<DoubleWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int count = 0;
	  int total = 0;
	  double relation = 0.0;
      //System.out.println("Start reducing");
      for (DoubleWritable val : values) {
        if(val.get() > 0.5){
			count++;
		}
        //System.out.println("getting " + key + " " + val.get());
		total++;
      }
	  relation = ((double)count) / total;
      result.set(Math.floor(relation*10000)/10000);
      //System.out.println("Result: " + result.toString()+"\nrelation: " + relation);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
	conf.set("mapreduce.output.textoutputformat.separator",",");
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: Part2 <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "Part 2");
    job.setJarByClass(Part2.class);
    job.setMapperClass(TokenizerMapper.class);
    //job.setCombinerClass(GeneReducer.class);
    job.setReducerClass(GeneReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(DoubleWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
