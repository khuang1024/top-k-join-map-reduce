import java.io.IOException;
//import java.util.Iterator;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.Mapper.Context;

public class TestReducer extends Reducer<Text, Text, LongWritable, Text> 
{	
	String finalKey = null;
	Text finalVal = new Text();
	String val=null;
	final Phrase2Pair output = new Phrase2Pair();
	final FloatWritable value = new FloatWritable();
	protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException,InterruptedException
	{
		for(Text v: values) 
		{
			String s = v.toString();
			if(s.substring(0,1).equals("S"))
			{
				val = s.substring(2);
			}
			else
			{
				finalKey = s.substring(2);
			}
		}
		finalVal.set(finalKey+";"+val+"_"+key.toString());
		context.write(null, finalVal);
	}	
}
