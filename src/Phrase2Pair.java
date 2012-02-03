import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


public class Phrase2Pair implements WritableComparable<Phrase2Pair>{

	public String Attribute;
	public float score;
	public String Pos_Relation;
	
	public void set(String left, String right)
	{
		Attribute = left;
		this.score = Float.parseFloat(right.substring(0,right.indexOf('_')));
		Pos_Relation = right;
	}
	public String getAttribute()
	{
		return Attribute;
	}
	public float getScore()
	{
		return this.score;
	}
	public String getScore_Pos_Relation()
	{
		return Pos_Relation;
	}
	public String getAll()
	{
		return "<"+Attribute+"-->"+"-->"+this.score+Pos_Relation+">";
	}
				
	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		Attribute = in.readLine();
		this.score = in.readFloat();
		Pos_Relation = in.readLine();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeBytes(Attribute+"\n");
		out.writeFloat(this.score);
		out.writeBytes(Pos_Relation+"\n");
	}
	
	public String toString() {
		return this.Attribute+"->"+"->"+this.score+"->"+this.Pos_Relation;
	}
	
	@Override
	public int compareTo(Phrase2Pair obj) {
		// TODO Auto-generated method stub
		if(!Attribute.equals(obj.Attribute))
		{
			return Attribute.compareTo(obj.Attribute);
		}
		else
		{
			return this.score==obj.score ? 0 : (this.score<obj.score ? 1:-1);
		}
	}
	
	public boolean equals(Object right)
	{
		if (0 ==compareTo((Phrase2Pair)right)) return true;
		return false;
	}
}
