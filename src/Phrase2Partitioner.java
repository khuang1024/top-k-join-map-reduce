import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;


public class Phrase2Partitioner extends Partitioner<LongWritable,Phrase2Pair>{

	@Override
	public int getPartition(LongWritable arg0, Phrase2Pair arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
