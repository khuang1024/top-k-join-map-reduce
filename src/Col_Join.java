import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Col_Join
{
	public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	    Job job = new Job(conf);
	    job.setJarByClass(Col_Join.class);
	    job.setMapperClass(TestMapper.class);
	    job.setReducerClass(TestReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);	    
	    //String input_path = "/input/R,/input/M";
	    String input_path = "/input/M,/input/R";
	    String output_path = "/output/";
	    
	    TextInputFormat.addInputPaths(job, input_path);
	    FileOutputFormat.setOutputPath(job, new Path(output_path));
	    double start=System.currentTimeMillis();
	    job.waitForCompletion(true) ;
	    double end=System.currentTimeMillis();
	    System.out.println("Phrase 1 time Consuming:" + (end-start)/1000 + " seconds");
	    
	    Job job_Phrase2 = new Job(conf);
	    job_Phrase2.setJarByClass(Col_Join.class);
	    job_Phrase2.setMapperClass(testPhrase2Mapper.class);
	    job_Phrase2.setReducerClass(testPhrase2Reducer.class);
	    job_Phrase2.setGroupingComparatorClass(GroupingComparator.class);

	    job_Phrase2.setMapOutputKeyClass(Phrase2Pair.class);
	    job_Phrase2.setMapOutputValueClass(Text.class);
	    job_Phrase2.setOutputKeyClass(Text.class);
	    job_Phrase2.setOutputValueClass(Text.class);
	    job_Phrase2.setInputFormatClass(TextInputFormat.class);
	    String inputPhrase2 = "/output/part-r-00000";
	    String outputPhrase2 = "/finaloutput/";
	    
	    TextInputFormat.addInputPaths(job_Phrase2, inputPhrase2);
	    FileOutputFormat.setOutputPath(job_Phrase2, new Path(outputPhrase2));
	    
	    double start2 = System.currentTimeMillis();
	    job_Phrase2.waitForCompletion(true) ;
	    double end2 = System.currentTimeMillis();
	    System.out.println("Phrase 2 time consuming:"+(end2-start2)/1000 + " seconds");
	}
}