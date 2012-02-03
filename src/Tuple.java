import java.util.Comparator;

//public class tuple implements Comparator<tuple>
public class Tuple implements Comparator<Tuple>
{
	float score;
	// e.g. three relations A, B and C are joined
	// new String[3], String[0]= A_Position, String[1]=B_Position
	// String[2] = C_Position
	String[] relations; 
	
	public Tuple()
	{
		//default constructor
		score = 0; 
		relations = new String[2];
	}
	public Tuple(float s, String left, String right)
	{
		
	}
	public Tuple(float s, String[] rel)
	{
		//default constructor
		this.score = s;
		relations = rel;
	}
	public void setTuple(float s, String[] rel)
	{
		this.score = s;
		this.relations= rel;
		//System.out.print("adding::<"+score+"->");
		/*for (int i = 0 ; i < relations.length; i++)
		{
			System.out.print(relations[i]+" & ");
		}
		System.out.println();*/
	}
	
	public String getTupleCombination()
	{
		String info = "";
		for (int i = 0 ; i < relations.length; i++)
		{
			info += " & "+relations[i];
		}
		return info;
	}
	
	@Override
	public int compare(Tuple first, Tuple second) {
		// TODO Auto-generated method stub
		if(first.score > second.score)
			return -1;
		else if (first.score==second.score) return 0;
		else return 1;
	}
	
	public boolean equals(Object obj)
	{
	  if (obj instanceof Tuple)
	  {
		  Tuple temptuple = (Tuple)obj;
		  if((this.score==temptuple.score)&&this.relations.equals(temptuple.relations))
		  {
			  return true;
		  }
		  else return false; 
	  }
	  return false;
	}
	
	public int hashCode()
	{
		Float fl=new Float(this.score);
		return fl.hashCode();
	}

}
