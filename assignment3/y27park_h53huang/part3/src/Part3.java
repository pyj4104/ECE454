
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.ArrayWritable;
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
	//private MapWritable mw = new MapWritable();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
		//StringTokenizer itr = new StringTokenizer(value.toString(),",");
		if(value == null || value.toString().equals("")){
            //System.out.println("empty");
            return;
        }
        String[] values = value.toString().split(",");
        if(values.length < 2){ 
            //System.out.println("too short, length is " + values.length);
            return;
        }
        //System.out.printf("Start map\nfirst item: %s, second item: %s ...\n",values[0],values[1]);
        TextArrayWritable taw = new TextArrayWritable(Arrays.copyOfRange(values,1,values.length));
		//System.out.printf("txtarrwritable: %s\n",taw.toString());
        MapWritable mw = new MapWritable();
        mw.put(new Text(values[0]),taw);
		//System.out.println("put into mapwritable");
        context.write(one,mw);
        //System.out.println("written to context");
    }
  }
  public static class TextArrayWritable extends ArrayWritable {
        public TextArrayWritable() {
            super(Text.class);
        }

        public TextArrayWritable(String[] strings) {
            super(Text.class);
            Text[] texts = new Text[strings.length];
            for (int i = 0; i < strings.length; i++) {
                texts[i] = new Text(strings[i]);
            }
            set(texts);
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            String prefix = "";
            for(String s : super.toStrings()){
                sb.append(prefix);
                prefix = ",";
                sb.append(s);
            }
            return sb.toString();
        }
    }
   
  public static class GeneReducer 
       extends Reducer<IntWritable,MapWritable,Text,DoubleWritable> {
    private DoubleWritable result = new DoubleWritable();

    public void reduce(IntWritable key, Iterable<MapWritable> values, 
	    Context context
	    ) throws IOException, InterruptedException {
		//System.out.println("Start reduce");
        ArrayList<MapWritable> cache = new ArrayList<MapWritable>();
		Iterator<MapWritable> it = values.iterator();
        while(it.hasNext()){
            MapWritable mw = it.next();  
            cache.add(new MapWritable(mw));
        }
		for(int i = 0; i < cache.size()-1; i++){
			for(int j = i+1; j < cache.size(); j++){
				Map.Entry entry = cache.get(i).entrySet().iterator().next();
				Map.Entry entry2 = cache.get(j).entrySet().iterator().next();
				double sum = 0.0;
                String[] entryStr = ((TextArrayWritable)entry.getValue()).toStrings();
                String[] entryStr2 = ((TextArrayWritable)entry2.getValue()).toStrings();
                //System.out.printf("first item:%s, second item:%s\n",Arrays.toString(entryStr),Arrays.toString(entryStr2));
				for(int k = 0; k < entryStr.length; k++){
                    //System.out.printf("%s * %s\n",entryStr[k],entryStr2[k]);
					sum += Double.parseDouble(entryStr[k]) * Double.parseDouble(entryStr2[k]);
                    //System.out.println("sum is " + sum);
				}
				result.set(Math.floor(sum*10000)/10000);
				context.write(new Text(entry.getKey().toString() + "," + entry2.getKey().toString()),result);
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
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(MapWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
