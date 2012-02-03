import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class SecondSort extends WritableComparator{

	protected SecondSort() {
		super(Phrase2Pair.class,true);
		// TODO Auto-generated constructor stub
	}
	public int compare(WritableComparable w1, WritableComparable w2)
	{		
		Phrase2Pair left = (Phrase2Pair)w1;
		Phrase2Pair right= (Phrase2Pair)w2;
		
		float l_score = Float.parseFloat(left.getScore_Pos_Relation().substring(0,left.getScore_Pos_Relation().indexOf('_')));
		float r_score = Float.parseFloat(right.getScore_Pos_Relation().substring(0,right.getScore_Pos_Relation().indexOf('_')));
		return l_score==r_score ? 0:(l_score<r_score ? 1:-1) ;
	}
}