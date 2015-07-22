
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Part3 {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, IntWritable, MapWritable>{
    
    private static final IntWritable one = new IntWritable(1);
	private MapWritable mw = new MapWritable();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
		//StringTokenizer itr = new StringTokenizer(value.toString(),",");
		String[] values = value.toString().split(",");
		mw.put(new Text(values[0]),new ArrayWritable(Arrays.copyOfRange(values,1,values.length)));
		context.write(one,mw);
    }
  }
  
  public static class GeneReducer 
       extends Reducer<IntWritable,MapWritable,Text,DoubleWritable> {
    private DoubleWritable result = new DoubleWritable();

    public void reduce(IntWritable key, Iterable<MapWritable> values, 
	    Context context
	    ) throws IOException, InterruptedException {
		ArrayList<MapWritable> cache = new ArrayList<MapWritable>();
		for(MapWritable mw : values){
			cache.add(new MapWritable(mw.get()));
		}
		
		for(int i = 0; i < cache.size(); i++){
			for(int j = i+1; j < cache.size(); j++){
				Map.Entry<Text,ArrayWritable> entry = cache[i].entrySet().iterator().next();
				Map.Entry<Text,ArrayWritable> entry2 = cache[j].entrySet().iterator().next();
				double sum = 0.0;
				for(int k = 0; k < entry.getValue().length; k++){
					sum += Double.parseDouble(entry.getValue()[k]) + Double.parseDouble(entry2.getValue()[k]);
				}
				result.set(sum);
				context.write(new Text(entry.toString() + "," + entry2.toString()),result);
			}
		}
		
		
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
	conf.set("mapreduce.output.textoutputformat.separator",",");
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: Part3 <in> <out>");
      System.exit(2);
    }
    Job job = new Job(conf, "Part 3");
    job.setJarByClass(Part3.class);
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
