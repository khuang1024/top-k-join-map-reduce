import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupingComparator extends WritableComparator{

	protected GroupingComparator() {
		super(Phrase2Pair.class,true);
		// TODO Auto-generated constructor stub
	}
	public int compare(WritableComparable w1, WritableComparable w2)
	{			
		Phrase2Pair left = (Phrase2Pair)w1;
		Phrase2Pair right= (Phrase2Pair)w2;
		return left.getAttribute().compareTo(right.getAttribute());	
	}
}