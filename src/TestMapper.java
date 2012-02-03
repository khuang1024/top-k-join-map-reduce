import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class TestMapper extends Mapper<LongWritable, Text, Text, Text> 
{	
		private String filename;
		private Text word = new Text();
		private String pos, attribute, actual_value;
		private char relation;
		
		//private org.apache.hadoop.io.Text input_file; 
		protected void setup(Context context) throws IOException, InterruptedException{
			//get how # of partitions
	        Configuration conf=context.getConfiguration();
	        //context.getConfiguration().gets
	        FileSplit fileSplit = (FileSplit)context.getInputSplit();
	        filename = fileSplit.getPath().getName();
	        attribute = filename.substring(2);
	        relation = filename.charAt(0);
		}

		//private final static IntWritable one = new IntWritable(1);
		Text outputKey = new Text();
		Text outputVal = new Text();
		protected void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException 
		{
			String[] line = value.toString().split(",");
			pos = line[1];
			actual_value = line[0];
			outputKey.set(pos+"_"+ relation);
			outputVal.set(attribute+"_"+actual_value);
			context.write(outputKey, outputVal);
		}
	 }