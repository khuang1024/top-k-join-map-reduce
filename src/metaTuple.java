import java.util.ArrayList;

public class metaTuple
{
	float score;
	String Pos_Relation;
	
	public metaTuple(float s, String pr)
	{
		this.score = s;
		this.Pos_Relation = pr;
	}
	
	public static ArrayList<Tuple> Join(int topk_threshold, ArrayList<metaTuple> l, ArrayList<metaTuple> r)
	{
		String[] tmp_pos_relation = new String[2];
		ArrayList<Tuple> results = new ArrayList<Tuple>();
		int counter =0;
		//Tuple temp; 
		for (int i =0 ; i < l.size(); i++)
		{
			for (int j = 0 ; j < r.size(); j++)
			{
				if(counter<topk_threshold)
				{
					float joinscore = l.get(i).score+r.get(j).score;
					tmp_pos_relation[0] = l.get(i).Pos_Relation;
					tmp_pos_relation[1] = r.get(j).Pos_Relation;
					//System.out.println("Constructing combination::<"+joinscore+","+tmp_pos_relation[0]+" & " + tmp_pos_relation[1]+">");
					Tuple temp = new Tuple();
					temp.setTuple(joinscore, tmp_pos_relation);
					results.add(temp);
					counter++;
				}
				else
					break;				
			}
		}
		return results;
	}
}

/*
public class metaTuple {
	float score[];
	String Pos_Relation[];
	int length; 
	int capacity; 
	public metaTuple(int capacity)
	{
		this.score = new float[capacity];
		this.Pos_Relation = new String[capacity];
		length = 0;
		this.capacity = capacity;
	}
	
	public void addMetaTuple(float s, String pr)
	{
		if(length<capacity)
		{
			this.score[length]=s;
			this.Pos_Relation[length]=pr;
			length++;
		}
		else
		{
			this.capacity *=2;
			float[] newScore = new float[capacity];
			newScore.
		}
	}
	
	public Tuple getCombination(metaTuple l,metaTuple r)
	{
		
	}
}
*/