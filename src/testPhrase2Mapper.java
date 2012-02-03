import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


// Phrase 2 mapper is used for group the immediate tuple based on 
// join attribute. 
public class testPhrase2Mapper extends Mapper<LongWritable, Text,Phrase2Pair, Text> 
{	
	Text val = new Text();
	final Phrase2Pair pair = new Phrase2Pair();

	protected void map(LongWritable key, Text value, Context context) throws IOException,InterruptedException 
	{
		String[] line = value.toString().split(";");
		pair.set(line[0].trim(),line[1].trim());
		val.set(line[1]);
		context.write(pair,val);
	}
 }